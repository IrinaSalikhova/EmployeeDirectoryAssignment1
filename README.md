  Please note, that the api call key in the code has billing information on it. If we accidentely exceed 1000 calls per day, it can be expensive!

  It can happen if call is anywhere in a loop and loop went infinite! Android itself calls several times too per each record. 

You have one fully working API with geo (however, feel free to change UI or working part of the code to make it better), and working one-call api (weatherData fetched properly, but you have to handle it and display)

Plus you need to make api calls for fetching icons with numbers recieved in one-call response (it is safe to use in loops). 
  https://docs.openweather.co.uk/weather-conditions#How-to-get-icon-URL
