# Javascript, jQuery and Json
### First Week / Second Week
- 배운 개념 : DB 연동 및 PRG 패턴을 적용해서 CRUD 기능 구현하기 
    - 메인화면([index.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/add.php)),  로그인([login.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/login.php)), 로그아웃([logout.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/logout.php)),  db 추가([add.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/add.php)),  db 수정([edit.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/edit.php)), db 삭제([delete.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/delete.php)), 상세보기([view.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/view.php))   
    - pdo 클래스([pdo.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/first%20week%20assignment/pdo.php))    

### Third Week   
- 배운 개념 : 제이쿼리를 활용하여 이벤트 처리   
    - [add.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/third%20week%20assignment/add.php), [edit.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/third%20week%20assignment/edit.php), [view.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/third%20week%20assignment/view.php) 파일에 position 테이블 정보 연동 추가   
    - add 버튼을 누르면 자동으로 position 정보 입력란이 생성되는 이벤트

### Forth Week
- 배운 개념 : 비동기통신과 JSON 데이터 사용하기([정리](https://junu0516.tistory.com/78?category=928437))    
    - [add.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/add.php), [edit.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/edit.php), [view.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/view.php) 파일에 education 테이블 정보 연동 추가   
    - [school.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/school.php)의 데이터를 JSON형식으로 받아와서 [add.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/add.php), [edit.php](https://github.com/junu0516/Coursera_Record/blob/main/Javascript%2C%20Jquery%20and%20Json/forth%20week%20assignment/edit.php) 파일에 학교 이름 일부 입력시, institution 테이블에서 입력값을 포함하는 모든 학교 이름을 불러와 보여주는 기능 추가
    - $('.school').autocomplete({source: "school.php"});