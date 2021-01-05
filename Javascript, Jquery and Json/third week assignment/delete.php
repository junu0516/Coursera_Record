<?php
    session_start();
    require_once "pdo.php";

    if(!isset($_SESSION['loginUser'])){
        die('Not logged in');
    }

    if(isset($_POST['cancel'])){
        header("Location: index.php");
        return;
    }
    
    if(isset($_POST['delete']) && isset($_GET['profile_id'])){
        $stmt = $pdo -> prepare('DELETE FROM Profile WHERE profile_id = :pid');
        $stmt -> execute(array(":pid" => $_GET['profile_id']));
        $_SESSION['success'] = "Record deleted";
        header("Location: index.php");
        return;
    }

    $stmt = $pdo -> prepare('SELECT first_name, last_name FROM Profile where profile_id = :pid');
    $stmt -> execute(array(":pid" => $_GET['profile_id']));
    $row = $stmt -> fetch(PDO::FETCH_ASSOC);



?>

<html>
<head>
<title>c4e31ce9</title>
</head>
<body>
<div class="container">
<h1>Deleteing Profile Confirm</h1>
<form method="post">
    <p>First Name: <?php echo($row['first_name']);?></p>
    <p>Last Name: <?php echo($row['last_name']);?></p>
    <input type="hidden" name="profile_id" value="<?php echo $GET_['profile_id'];?>"/>
    <input type="submit" name="delete" value="Delete">
    <input type="submit" name="cancel" value="Cancel">
</form>
</div>
</body>
</html>