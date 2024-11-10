import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.employeedirectoryassignment1.weatherApp.CityResponse
import com.example.employeedirectoryassignment1.weatherApp.WeatherApiService
import com.example.employeedirectoryassignment1.weatherApp.WeatherViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var fakeRepository: WeatherApiService // Mock your repository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = WeatherViewModel(fakeRepository) // Pass the mocked repository
    }

    @Test
    fun `fetchCityList should set error message on failure`() = runTest {
        // Given
        val cityName = "InvalidCity"
        Dispatchers.setMain(StandardTestDispatcher(testScheduler)) // Set up dispatcher for this test

        // Simulate an error in the repository
        `when`(fakeRepository.getCityList(cityName)).thenThrow(RuntimeException("API Error"))

        // When
        viewModel.fetchCityList(cityName, 10, "apiKey")

        // Then
        // Collect the error message from LiveData
        val errorMessage = viewModel.errorMessage?.first() // Use first() to get the value
        assertEquals("Failed to fetch data", errorMessage)
    }

    @Test
    fun `fetchCityList should update city list on success`() = runTest {
        // Given
        val cityName = "Ottawa"
        val expectedCityList = listOf(CityResponse("Ottawa", 45.4215, -75.6972, "CA", "Ontario"))
        Dispatchers.setMain(StandardTestDispatcher(testScheduler)) // Set up dispatcher for this test

        // Simulate a successful response in the repository
        `when`(fakeRepository.getCityList(cityName)).thenReturn(expectedCityList)

        // When
        viewModel.fetchCityList(cityName, 10, "apiKey")

        // Then
        // Collect the city list from LiveData
        val cityList = viewModel.cityList.first() // Use first() to get the value
        assertEquals(expectedCityList, cityList)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }
}