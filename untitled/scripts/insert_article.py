import requests
import random
import string

# 랜덤한 문자열 생성 함수
def random_string(length):
    letters = string.ascii_letters + string.digits
    return ''.join(random.choice(letters) for i in range(length))

# 로그인 함수
def login(base_url, login_id, password):
    login_url = f"{base_url}/api/members/login"
    login_data = {
        "loginId": login_id,
        "password": password
    }
    session = requests.Session()
    response = session.post(login_url, data=login_data)  # form data 형식으로 전송
    if response.status_code == 200 or response.status_code == 302:  # Spring Security는 리다이렉트할 수 있습니다.
        print("Login successful")
        return session
    else:
        print(f"Login failed: {response.status_code} {response.text}")
        return None

# 게시글 정보를 대량으로 추가하는 함수
def bulk_add_articles(base_url, session, count):
    url = f"{base_url}/api/articles"
    for i in range(count):
        title = random_string(10)
        content = random_string(50)

        data = {
            "title": title,
            "content": content
        }

        response = session.post(url, json=data)
        if response.status_code == 201:
            print(f"Article {i+1} added successfully")
        else:
            print(f"Failed to add article {i+1}: {response.status_code} {response.text}")

# 기본 URL 설정
base_url = "http://localhost:8081"

# 로그인 정보 설정
login_id = "test1"
password = "Test12345!@"

# 로그인 수행
session = login(base_url, login_id, password)

if session:
    # 10,000개의 게시글 정보를 추가
    bulk_add_articles(base_url, session, 10000)
