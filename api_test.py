import os


# TIP:MODULE_SCHEDULETRIP: A simple python script that sends a bunch of curl requests
# to your server to test your REST APIs.
# Change the ip address & port if need be.
def api_test():
  ip_address = 'http://localhost'
  port = '8081'
  api_input = {}

  api_input['car_details'] = '''{
    "carId": "CAR_ID_1"
    }'''

  api_input['cars_in_location'] = '''
    {
    "geoLocation": {
    "latitude": 12.908486,
    "longitude": 77.536386
    }
    }
    '''

  api_input['car_status'] = '''
    {
    "carId": "CAR_ID_1"
    }
    '''

  api_input['complete_trip'] = '''
    {
    "tripId": "TRIP_ID_1",
    "finishLocation": {
    "latitude": 12.908486,
    "longitude": 77.536386
    },
    "paymentMode": "CASH_PAYMENT"
    }
    '''

  api_input['fare_estimates'] = '''
    {
    "sourceLocation": {
    "latitude": 12.908486,
    "longitude": 77.536386
    },
    "destinationLocation": {
    "latitude": 12.708486,
    "longitude": 77.036386
    }
    }
    '''

  api_input['schedule_trip'] = '''
    {
    "carType": "CAR_TYPE_HATCHBACK",
    "tripPrice": 129.00,
    "sourceLocation": {
    "latitude": 12.908486,
    "longitude": 77.536386
    },
    "destinationLocation": {
    "latitude": 12.908486,
    "longitude": 78.536386
    }
    }
    '''

  for api in api_input:
    curl_command = 'curl -k -i -H "Content-Type:application/json" -d '
    curl_command += '\'' + api_input[api] + '\' '
    curl_command += ip_address + ':' + port
    curl_command += '/qride/v1/' + api
    print("\nExecuting command: " + curl_command)
    os.system(curl_command)
  print("\n")


if __name__ == "__main__":
  api_test()
