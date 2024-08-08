import requests
import random
import string

# 랜덤한 문자열 생성 함수
def random_string(length):
    letters = string.ascii_letters + string.digits
    return ''.join(random.choice(letters) for i in range(length))

# 랜덤한 전화번호 생성 함수
def random_phone_number():
    return f"010-{random.randint(1000, 9999)}-{random.randint(1000, 9999)}"

# 랜덤한 이메일 생성 함수
def random_email():
    domains = ["example.com", "test.com", "mail.com"]
    return f"{random_string(8)}@{random.choice(domains)}"

# 회원 정보를 대량으로 추가하는 함수
def bulk_add_members(count):
    url = "http://localhost:8081/api/members"
    for i in range(count):
        login_id = random_string(8)
        password = "Test12345!@"
        name = f"Test User {i}"
        phone_number = random_phone_number()
        email = random_email()
        role = "USER"  # 기본 역할은 USER로 설정

        data = {
            "loginId": login_id,
            "password": password,
            "name": name,
            "phoneNumber": phone_number,
            "department": "FRONTEND",
            "email": email,
            "role": role
        }

        response = requests.post(url, json=data)
        if response.status_code == 201:
            print(f"Member {i+1} added successfully")
        else:
            print(f"Failed to add member {i+1}: {response.status_code} {response.text}")

# 10,000명의 회원 정보를 추가
bulk_add_members(100)
def add_admin_member():
    url = "http://localhost:8081/api/members"
    role = "ADMIN"
    login_id = "adminId"
    password = "Test12345!@"

    data = {
        "loginId": login_id,
        "password": password,
        "name": f"{role} User",
        "phoneNumber": random_phone_number(),
        "email": random_email(),
        "adminCode": "admin_code",
        "department": "FRONTEND",
        "role": role
    }

    response = requests.post(url, json=data)
    if response.status_code == 201:
        print(f"{role} member added successfully")
    else:
        print(f"Failed to add {role} member: {response.status_code} {response.text}")

# 관리자 계정 추가 실행
add_admin_member()

def add_user_member():
    url = "http://localhost:8081/api/members"
    role = "USER"
    login_id = "userId"
    password = "Test12345!@"

    data = {
        "loginId": login_id,
        "password": password,
        "name": f"{role} User",
        "phoneNumber": random_phone_number(),
        "email": random_email(),
        "department": "FRONTEND",
        "role": role
    }

    response = requests.post(url, json=data)
    if response.status_code == 201:
        print(f"{role} member added successfully")
    else:
        print(f"Failed to add {role} member: {response.status_code} {response.text}")

# 관리자 계정 추가 실행
add_user_member()



