<?php
    session_start();
    require_once "pdo.php";

    if(!isset($_SESSION['loginUser'])){
        die("Not logged in");
    }

    if (isset($_POST['cancel'])) {
        header("Location: index.php");
        return;
    }

    if ( isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['profile_id'])
     && isset($_POST['email']) && isset($_POST['headline']) && isset($_POST['summary'])) {

        // Data validation
        if (strlen($_POST['first_name']) < 1 || strlen($_POST['last_name']) < 1 || strlen($_POST['email']) < 1 || strlen($_POST['headline']) < 1 || strlen($_POST['summary']) < 1) {
            $_SESSION['error'] = 'All fields are required';
            header("Location: edit.php?profile_id=".$_POST['profile_id']);
            return;
        }else if (strpos($_POST['email'], '@') == false) {
            $_SESSION['error'] = 'The mail must have an at (@) sign';
            header("Location: edit.php?profile_id=".$_POST['profile_id']);
            return;
        }else {
            $stmt = $pdo->prepare('UPDATE Profile SET
                            first_name = :fn,
                            last_name = :ln,
                            email = :em,
                            headline = :he,
                            summary = :su
                            WHERE profile_id = :pid'              
                            );
            $stmt->execute(array(
                ':pid' => $_POST['profile_id'],
                ':fn' => $_POST['first_name'],
                ':ln' => $_POST['last_name'],
                ':em' => $_POST['email'],
                ':he' => $_POST['headline'],
                ':su' => $_POST['summary'])
                );

                $_SESSION['success'] = 'Record Updated';
                header( 'Location: index.php' ) ;
                return;
            }
    }

    $stmt = $pdo -> prepare("SELECT * FROM  Profile WHERE profile_id = :pid");
    $stmt -> execute(array(":pid" => $_GET['profile_id']));
    $rows = $stmt -> fetch(PDO::FETCH_ASSOC); //fetch, fetchAll의 차이를 알아두자
    if($rows === false){
        $_SESSION['error'] = "Bad values for profile_id";
        header("Location: index.php");
        return;
    }
    
    $fn = htmlentities($rows['first_name']);
    $ln = htmlentities($rows['last_name']);
    $em = htmlentities($rows['email']);
    $he = htmlentities($rows['headline']);
    $su = htmlentities($rows['summary']);
    $pid = htmlentities($rows['profile_id']);
?>


<html>
<head>
<title>5bc8eea2</title>
</head>
<body>
<div class="container">
    <?php
        // Flash pattern
        if ( isset($_SESSION['error']) ) {
            echo '<p style="color:red">'.$_SESSION['error']."</p>\n";
            unset($_SESSION['error']);
        }
    ?>
    <h1>Editing Profile</h1>
    <form method="post" action="edit.php">
        <input type="hidden" nane="profile_id" value="<?= $pid?>">
        <p>First Name:
            <input type="text" name="first_name" size="60" value="<?= $fn ?>"/></p>
        <p>Last Name:
            <input type="text" name="last_name" size="60" value="<?= $ln ?>"/></p>
        <p>Email:
            <input type="text" name="email" size="30" value="<?= $em ?>"/></p>
        <p>Headline:<br/>
            <input type="text" name="headline" size="80" value="<?= $he ?>"/></p>
        <p>Summary:<br/>
            <textarea name="summary" rows="8" cols="80"><?= $su ?></textarea>
        <p>
            <input type="hidden" name="profile_id" value="<?= $pid ?>"/>
            <input type="submit" value="Save">
            <input type="submit" name="cancel" value="Cancel">
        </p>
    </form>

</div>
</body>
</html>