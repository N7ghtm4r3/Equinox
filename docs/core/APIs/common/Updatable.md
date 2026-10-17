The `Updatable` interface wraps the behavior used to update an entity's details behind a single `update` operation.
Each entity encapsulates its own update rules, so callers do not need to know which properties change or how the
changes are applied

The type parameter `D` represents the details accepted by `update(newDetails: D?)`.
Each implementation defines how to apply those details, including the meaning of `null`

## Usage

The product encapsulates the assignments inside `update`, so callers can update its details with a single operation:

```kotlin
import com.tecknobit.equinoxcore.entities.Updatable

data class ProductDetails(val name: String, val price: Double)

class Product(var name: String, var price: Double) : Updatable<ProductDetails> {

    override fun update(newDetails: ProductDetails?) { 
        name = newDetails.name
        price = newDetails.price
    }
}

val product = Product(
    name = "Original name",
    price = 10.0
)

val newProduct = ProductDetails(
    name = "Updated name",
    price = 12.0
)

product.update(newProduct)
```

The caller does not need to assign `name` and `price` separately. If the product's update rules change, those details
remain inside `update` and callers continue to use the same operation
