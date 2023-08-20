from locust import HttpUser, task, between
import random

class User(HttpUser):
    wait_time = between(0.5, 0.2)

    def update_key_value(self):
        self.key = random.randint(1, 1000)
        self.value = random.randint(1, 1000)

    @task
    def put_data(self):
        self.update_key_value()
        url = f'/proxy/store/{self.key}'
        headers = {'Content-Type': 'application/json'}
        data = {'key': f'{self.key}', 'value': f'{self.value} value'}
        self.client.put(url, json=data)

    @task
    def get_data(self):
        url = f'/proxy/store/{self.key}'
        self.client.get(url)
