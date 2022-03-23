package com.riac.marketapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.riac.marketapp.data.local.AppDao
import com.riac.marketapp.data.local.RoomRepository
import com.riac.marketapp.domain.repository.MarketRepository
import com.riac.marketapp.presentation.MainViewModel
import com.riac.marketapp.util.DispatcherProvider
import com.riac.marketapp.util.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private var repository: MarketRepository = mockk()
    private var dispatcherProvider: DispatcherProvider = mockk()
    private var coDispatcher: CoroutineDispatcher = mockk()
    private var appDao: AppDao = mockk()
    private var roomRepository: RoomRepository = mockk()
    private lateinit var viewModel: MainViewModel


    private val searchViewValue = "bizarap"

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository, dispatcherProvider, roomRepository)
        coEvery { dispatcherProvider.default } returns coDispatcher
    }

    @After
    fun teardown() {
    }


    /**
    This is for testing if the EnvironmentViewModel() has been instantiated correctly.
     */
    @Test
    fun `Check that ViewModel is not null`() = Assert.assertNotNull(viewModel)

    @Test
    fun `Check initial state of recentItems LiveData value`() {
        // Given

        // When
        // Do nothing

        // Then
        Assert.assertNull(viewModel.recentItems.value)
    }

    @Test
    fun `Check initial state of searchItem LiveData value`() {
        // Given

        // When
        // Do nothing

        // Then
        Assert.assertNull(viewModel.searchItem.value)
    }

    @Test
    fun `Check initial state of searchQuery LiveData value`() {
        // Given

        // When
        // Do nothing

        // Then
        Assert.assertNull(viewModel.searchQuery.value)
    }

    @Test
    fun `Check initial state of selectedItem LiveData value`() {
        // Given

        // When
        // Do nothing

        // Then
        Assert.assertNull(viewModel.selectedItem.value)
    }

    /**
    This is for testing if calling makeSearch() will have a result different than null
     */
    @Test
    fun `Check the stability of the makeSearch method`() {
        // Given


        // When
        val result = viewModel.makeSearch(searchViewValue)

        // Then
        Assert.assertNotNull(result)
    }
}