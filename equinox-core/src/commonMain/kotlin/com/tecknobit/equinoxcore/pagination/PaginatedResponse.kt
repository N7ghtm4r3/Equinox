package com.tecknobit.equinoxcore.pagination

import com.tecknobit.equinoxcore.helpers.JsonHelper
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonObject

/**
 * The **PaginatedResponse** class is formatter for the responses of pagination requests providing easy access
 * to page data and items data retrieved
 *
 * @property T the type of the items in the [data] list
 *
 * @since 1.0.5
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class PaginatedResponse<T> {

    companion object {

        /**
         * **DATA_KEY** -> the key of the "data" value
         */
        const val DATA_KEY: String = "data"

        /**
         * **PAGE_KEY** -> the key of the "page" value
         */
        const val PAGE_KEY: String = "page"

        /**
         * **DATA_SIZE_KEY** -> the key of the "data_size" value
         */
        const val DATA_SIZE_KEY: String = "data_size"

        /**
         * **PAGE_SIZE_KEY** -> the key of the "page_size" value
         */
        const val PAGE_SIZE_KEY: String = "page_size"

        /**
         * **IS_LAST_PAGE_KEY** -> the key of the "is_last_page" value
         */
        const val IS_LAST_PAGE_KEY: String = "is_last_page"

        /**
         * **DEFAULT_PAGE** -> the default page value
         */
        const val DEFAULT_PAGE: Int = 0

        /**
         * **DEFAULT_PAGE_HEADER_VALUE** -> the default page value as header string
         */
        const val DEFAULT_PAGE_HEADER_VALUE: String = "0"

        /**
         * **DEFAULT_PAGE_SIZE** -> the default size of the page
         */
        const val DEFAULT_PAGE_SIZE: Int = 10

        /**
         * **DEFAULT_PAGE_SIZE_HEADER_VALUE** -> the default size of the page as header string
         */
        const val DEFAULT_PAGE_SIZE_HEADER_VALUE: String = "10"

    }

    /**
     * **data** -> the data retrieved by the request
     */
    val data: List<T>

    /**
     * **page** -> the number of the page requested
     */
    val page: Int

    /**
     * **pageSize** -> the size of the maximum items for page
     */
    @SerialName(PAGE_SIZE_KEY)
    val pageSize: Int

    /**
     * **previousPage** -> the number of the previous page
     */
    @kotlinx.serialization.Transient
    val previousPage: Int
        get() = page - 1

    /**
     * **nextPage** -> the number of the next page
     */
    @kotlinx.serialization.Transient
    val nextPage: Int
        get() = page + 1

    /**
     * **isLastPage** -> whether the current [page] is the last one available
     */
    @SerialName(IS_LAST_PAGE_KEY)
    val isLastPage: Boolean

    /**
     * **dataSize** -> the size of the [data] retrieved for the current [page]
     */
    @SerialName(DATA_SIZE_KEY)
    val dataSize: Int
        get() = data.size

    /**
     * Constructor to init the [PaginatedResponse]
     *
     * @param data The data retrieved by the request
     * @param page The number of the page requested
     * @param pageSize The size of the maximum items for page
     * @param totalDataCount The total amount of data available, it is used to automatically
     * detect if the [page] is the last one available
     */
    constructor(data: List<T>, page: Int, pageSize: Int, totalDataCount: Long) {
        this.data = data
        this.page = page
        this.pageSize = pageSize
        val balancer = if ((totalDataCount % pageSize) == 0L) 1
        else 0
        val maxPages = (totalDataCount / pageSize).toInt() - balancer
        this.isLastPage = totalDataCount < pageSize || page >= maxPages
    }

    /**
     * Constructor to init the [PaginatedResponse]
     *
     * @param data The data retrieved by the request
     * @param page The number of the page requested
     * @param pageSize The size of the maximum items for page
     * @param isLastPage Whether the retrieved [page] is the last available
     */
    constructor(data: List<T>, page: Int, pageSize: Int, isLastPage: Boolean) {
        this.data = data
        this.page = page
        this.pageSize = pageSize
        this.isLastPage = isLastPage
    }

    /**
     * Constructor to init the [PaginatedResponse]
     *
     * @param hPage The helper to fetch from the response the data to use for the [PaginatedResponse]
     * @param supplier The supplier Method to instantiate the [T] item for the [data] list
     */
    constructor(
        hPage: JsonHelper,
        supplier: (JsonObject) -> T,
    ) {
        val jData: ArrayList<JsonObject> = hPage.fetchList(DATA_KEY)
        val data = arrayListOf<T>()
        jData.forEach { item ->
            val instantiatedItem = supplier.invoke(item)
            data.add(instantiatedItem)
        }
        this.data = data
        page = hPage.getInt(PAGE_KEY)
        pageSize = hPage.getInt(PAGE_SIZE_KEY)
        isLastPage = hPage.getBoolean(IS_LAST_PAGE_KEY)
    }

}
