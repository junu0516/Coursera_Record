<?php
    session_start();
    require_once "pdo.php";

    if (isset($_POST['cancel'])) {
        header("Location: index.php");
        return;
    }

    $salt = 'XyZzy12*_';
    if (isset($_POST['pass']) && isset($_POST['email'])) {
        if( strlen($_POST['email']) < 1 || strlen($_POST['pass']) < 1 ) {
            $_SESSION['loginError'] = "Email and password are required";
            header("Location: login.php");
            return;
        }else if(strpos($_POST['email'], '@') == false){
            $_SESSION['emailError'] = "Email must have an at-sign (@)";
            header("Location: login.php");
            return;
        }else{
            $check = hash('md5', $salt . $_POST['pass']);
            $stmt = $pdo->prepare('SELECT user_id, name FROM users WHERE email = :em AND password = :pw');
            $stmt->execute(array(':em' => $_POST['email'], ':pw' => $check));
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
        
            if ($row !== false) {
                $_SESSION['loginUser'] = $row['name'];
                $_SESSION['user_id'] = $row['user_id'];
                header("Location: index.php");
                return;
            }else{
                $_SESSION['incorrectPwd'] = "Incorrect Password";
                header("Location: login.php");
                return;
            } 
        }
    }
?>
<!DOCTYPE html>
<html>
<head>
    <title>5bc8eea2</title>
</head>
<body>
<div class="container">
    <h1>Please Log In</h1>
    <?php
        if (isset($_SESSION['loginError'])) {
            echo('<p style="color: red;">' . htmlentities($_SESSION['loginError']) . "</p>\n");
            unset($_SESSION['loginError']);
        }else if(isset($_SESSION['incorrectPwd'])){
            echo('<p style="color: red;">' . htmlentities($_SESSION['incorrectPwd']) . "</p>\n");
            unset($_SESSION['incorrectPwd']);
        }else if( isset($_SESSION['emailError']) ) {
            echo('<p style="color: red;">'.htmlentities($_SESSION['emailError'])."</p>\n");
            unset($_SESSION['emailError']);
        }
    ?>
    <form method="POST">
        User Name <input type="text" name="email"><br/>
        Password <input type="text" name="pass"><br/>
        <input type="submit" value="Log In">
        <input type="submit" name="cancel" value="Cancel">
    </form>
</div>
</body>
</html>