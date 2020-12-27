## 로그인 후, PRG 패턴 적용해서 정보 입력 후 DB에 추가 & 출력하기    
- POST-Redirect-GET 패턴이 왜 필요한지는 [여기](https://junu0516.tistory.com/76?category=933252)를 참고하자   
- redirect를 위해서는 header("url")을 활용   
- Code Injection의 방지([add.php](https://github.com/junu0516/Coursera_Record/blob/main)참고)
    - htmlentities($_SESSION['name']) 사용 : 주어진 모든 문자를 html 엔티티로 변환(영어만 사용할 때 유효)      
    - PDO 객체 사용시, prepare() 통해 쿼리문 적용 : 인자값을 바인딩
