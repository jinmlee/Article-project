import pymysql
import random
import string
from datetime import datetime, timedelta

# 랜덤한 문자열 생성 함수
def random_string(length):
    letters = string.ascii_letters + string.digits
    return ''.join(random.choice(letters) for i in range(length))

# 지난 한 달 내의 랜덤 시간 생성 함수
def random_time_within_last_month():
    now = datetime.now()
    random_days = random.randint(0, 30)
    random_seconds = random.randint(0, 24 * 60 * 60)
    random_time = now - timedelta(days=random_days, seconds=random_seconds)
    return random_time.strftime('%Y-%m-%d %H:%M:%S')

# 데이터베이스 연결 설정
conn = pymysql.connect(
    host='localhost',
    user='root',
    password='wls960aha!',
    db='article',
    charset='utf8mb4',
    cursorclass=pymysql.cursors.DictCursor,
    ssl_disabled=True
)
cur = conn.cursor()

# 아티클 데이터 삽입 함수
def insert_articles(num_articles):
    for i in range(num_articles):
        title = random_string(10)
        content = random_string(50)
        member_id = random.randint(1, 100)
        created_date = random_time_within_last_month()
        modified_date = created_date  # 수정 시간도 생성 시간과 동일하게 설정

        cur.execute("""
            INSERT INTO article (title, content, hits, member_id, created_date, modified_date)
            VALUES (%s, %s, %s, %s, %s, %s)
        """, (title, content, 0, member_id, created_date, modified_date))
        if (i + 1) % 100 == 0:
            print(f"Inserted {i + 1} articles")
            conn.commit()  # 주기적으로 커밋하여 성능 향상

# 아티클 데이터 삽입
num_articles = 10000
insert_articles(num_articles)

# 최종 커밋 및 연결 종료
conn.commit()
cur.close()
conn.close()
print("All articles inserted successfully")
