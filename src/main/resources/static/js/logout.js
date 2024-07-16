const logoutButton = document.getElementById('logout-btn');

if(logoutButton){
    logoutButton.addEventListener('click', event => {
        fetch('/api/members/logout', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(() => {
            alert("로그아웃 되었습니다.")
            return location.replace("/articleList")
        })
    })
}