# Soft deletion

Soft deletion allows to keep an entity in the database after deleting it, so it can be restored later.
Hard deletion permanently removes the record instead

Available since **2.0.0**, the built-in user environment already supports both operations. You can also
add them to your own entities by creating a dedicated repository and service

## User accounts

### Delete an account

To soft delete an account, send the following request with the user's token:

```http
DELETE /api/v2/users/{user_id}
token: <user-token>
```

The account and its profile pic are preserved, but the user is no longer authenticated by the
[token filter](Security/authentication/EquinoxTokenAuthenticationFilter.md). The built-in service also
prevents changes to the profile pic, email, password, and language of a soft deleted account

To permanently delete the account, add `hard_delete=true`:

```http
DELETE /api/v2/users/{user_id}?hard_delete=true
token: <user-token>
```

Both requests require the requested identifier to match the authenticated user, as explained in the
[user authorization guide](Security/authorization/annotations/IsAuthorizedUser.md). Choose the deletion
mode while the account is active; after soft deletion, its token no longer authenticates these requests

Hard deletion removes the account and then attempts to delete its profile resource. The account
response does not report whether the file was removed

If you need to delete an account from your backend service, use `deleteUser()` with `false` for soft
deletion or `true` for permanent deletion:

=== "Java"

    ```java
    usersService.deleteUser(userId, false);
    ```

=== "Kotlin"

    ```kotlin
    usersService.deleteUser(userId, false)
    ```

### Authentication and restoration

To restore a soft deleted account, the user signs in again with their credentials. The built-in
`signInUser(SignInRequestDto)` flow restores the account after successful validation, preserving its
identifier, token, and profile pic

This behavior is already available when extending `EquinoxUsersService`. If you customize sign-in,
follow the [dedicated guide](Environment.md#custom-sign-in) and retain the credential checks.
The deprecated sign-in overload does not restore accounts

To check whether an active user exists, use `userExists(userId)`. When you also need to include soft
deleted accounts, pass `true`:

=== "Java"

    ```java
    boolean accountExists = usersService.userExists(userId, true);
    ```

=== "Kotlin"

    ```kotlin
    val accountExists = usersService.userExists(userId, true)
    ```

## Implementation for custom entities

The following example adds soft deletion to a `Car` entity. If you are customizing users instead,
`EquinoxUsersRepository` and `EquinoxUsersService` already provide this setup: you can proceed to
[guarding your custom operations](#guard-custom-operations)

### Create the entity

Create your entity extending `EquinoxItem`, which provides the identifier and the deletion state:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Table;

    @Entity
    @Table(name = "cars")
    public class Car extends EquinoxItem {

        @Column(name = "model")
        private String model;

        protected Car() {
            super((String) null);
        }

        public Car(String id, String model) {
            super(id);
            this.model = model;
        }

        public String getModel() {
            return model;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem
    import jakarta.persistence.Column
    import jakarta.persistence.Entity
    import jakarta.persistence.Table

    @Entity
    @Table(name = "cars")
    open class Car : EquinoxItem {

        @field:Column(name = "model")
        open var model: String = ""
            protected set

        protected constructor() : super(null as String?)

        constructor(id: String, model: String) : super(id) {
            this.model = model
        }

    }
    ```

`EquinoxItem` maps the state to the `deleted_at` column. The value `-1` means the entity is active;
soft deletion replaces it with the current timestamp in milliseconds. Restoring the entity sets it
back to `-1`

When updating an existing database, include the inherited `deleted_at` column in your schema and
initialize existing active records to `-1`

### Create the dedicated repository

Extend `EquinoxCrudRepository<Car>` and provide the queries used to delete and restore your entity.
For the `cars` table, both operations update the inherited `deleted_at` column:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.builtin.repositories.EquinoxCrudRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface CarsRepository extends EquinoxCrudRepository<Car> {

        @Override
        @Modifying(clearAutomatically = true)
        @Query(value = "UPDATE cars SET deleted_at = :deleted_at WHERE id = :id", nativeQuery = true)
        void softDelete(@Param("id") String id, @Param("deleted_at") long deletedAt);

        @Override
        @Modifying(clearAutomatically = true)
        @Query(value = "UPDATE cars SET deleted_at = -1 WHERE id = :id", nativeQuery = true)
        void restoreSoftDeletedEntity(@Param("id") String id);

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.builtin.repositories.EquinoxCrudRepository
    import org.springframework.data.jpa.repository.Modifying
    import org.springframework.data.jpa.repository.Query
    import org.springframework.data.repository.query.Param
    import org.springframework.stereotype.Repository

    @Repository
    interface CarsRepository : EquinoxCrudRepository<Car> {

        @Modifying(clearAutomatically = true)
        @Query(value = "UPDATE cars SET deleted_at = :deleted_at WHERE id = :id", nativeQuery = true)
        override fun softDelete(@Param("id") id: String, @Param("deleted_at") deletedAt: Long)

        @Modifying(clearAutomatically = true)
        @Query(value = "UPDATE cars SET deleted_at = -1 WHERE id = :id", nativeQuery = true)
        override fun restoreSoftDeletedEntity(@Param("id") id: String)

    }
    ```

Adapt the table name to your entity. The base repository declares these operations, so the concrete
repository must supply their implementation

### Create the dedicated service

Create a service extending `EquinoxCrudService<Car>` and pass your repository to the base constructor:

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.builtin.services.EquinoxCrudService;
    import org.springframework.stereotype.Service;

    @Service
    public class CarsService extends EquinoxCrudService<Car> {

        private final CarsRepository carsRepository;

        public CarsService(CarsRepository carsRepository) {
            super(carsRepository);
            this.carsRepository = carsRepository;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.builtin.services.EquinoxCrudService
    import org.springframework.stereotype.Service

    @Service
    open class CarsService(
        private val carsRepository: CarsRepository
    ) : EquinoxCrudService<Car>(carsRepository)
    ```

You can now use the inherited `deleteEntity()` method to soft delete a car:

=== "Java"

    ```java
    carsService.deleteEntity(carId, false);
    ```

=== "Kotlin"

    ```kotlin
    carsService.deleteEntity(carId, false)
    ```

Pass `true` instead to permanently delete it. The service manages the database transaction for both
operations. Any application-specific cleanup can be added by overriding `hardDelete()` or `softDelete()`

### Guard custom operations

Deleting a record softly does not hide it from repository queries such as `findById()` or `findAll()`.
When adding an operation that requires an active entity, use `requireEntityNotSoftDeleted()` to check
it before running your logic

For example, add this method inside `CarsService` to retrieve the model of an active car:

=== "Java"

    ```java
    import java.util.function.Function;

    public String getCarModel(String carId) {
        Function<Car, String> readModel = Car::getModel;
        return requireEntityNotSoftDeleted(carId, readModel);
    }
    ```

=== "Kotlin"

    ```kotlin
    import java.util.function.Function

    fun getCarModel(carId: String): String {
        val readModel = Function<Car, String> { car -> car.model }
        return requireEntityNotSoftDeleted(carId, readModel)
    }
    ```

The callback runs only if the car exists and is active. Otherwise, the guard throws
`EquinoxForbiddenException`. Use the same approach before custom updates, and filter soft deleted
records explicitly when building list queries

For custom users, the guard is inherited from `EquinoxUsersService` and can be used in the same way
inside your [dedicated service](Environment.md#creating-the-dedicated-service). The built-in account
updates already apply it

### Restore the entity

To let your application restore a car, add a transactional method to `CarsService` that retrieves it
and calls the inherited `restoreSoftDeletedEntity()` method:

=== "Java"

    ```java
    import jakarta.transaction.Transactional;

    @Transactional
    public void restoreCar(String carId) {
        Car car = carsRepository.findById(carId).orElseThrow();
        restoreSoftDeletedEntity(car);
    }
    ```

=== "Kotlin"

    ```kotlin
    import jakarta.transaction.Transactional

    @Transactional
    open fun restoreCar(carId: String) {
        val car = carsRepository.findById(carId).orElseThrow()
        restoreSoftDeletedEntity(car)
    }
    ```

In this example, an unknown identifier causes `orElseThrow()` to throw `NoSuchElementException`.
For an existing car, restoration updates both the database and the supplied instance to the active
state. Calling `setDeletedAt(-1)` alone would only change the in-memory instance

You can now invoke `restoreCar()` from the application flow that should restore the car.
Unlike user sign-in, custom entities do not have an automatic restoration trigger
