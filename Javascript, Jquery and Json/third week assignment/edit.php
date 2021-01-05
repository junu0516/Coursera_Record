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
            
            
            $stmt2 = $pdo -> prepare('DELETE FROM position WHERE profile_id=:pid');
            $stmt2 -> execute(array(':pid'=> $_POST['profile_id'])); //delete previous information

            //subsutitue with new information
            $rank = 1;
            for($i = 1;$i<=9;$i++){
                if ( ! isset($_POST['year'.$i])){
                    continue;
                } 
                if ( ! isset($_POST['desc'.$i])){
                    continue;
                }

                $year = $_POST['year'.$i]; //year
                if(!is_numeric($year)){
                    $_SESSION['error'] = 'Year must be numeric value';
                    header("Location: add.php");
                    return;
                }
                $desc = $_POST['desc'.$i]; //description

                $stmt = $pdo->prepare('INSERT INTO Position
                (profile_id, rank, year, description) VALUES (:pid, :rnk, :year, :descr)');
                $stmt->execute(array(
                    ':pid' => $_POST['profile_id'],
                    ':rnk' => $rank,
                    ':year' => $year,
                    ':descr' => $desc
                ));
                $rank++;
            }

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

    $stmt2 = $pdo -> prepare("SELECT * FROM position WHERE profile_id = :pid");
    $stmt2 -> execute(array(':pid' => $_GET['profile_id']));
    $row2 = $stmt2 -> fetchAll(PDO::FETCH_ASSOC);

    if($row2 === false){
        $_SESSION['error'] = "Bad values for profile_id";
        header("Location: index.php");
        return;
    }
    
?>


<html>
<head>
<title>c4e31ce9</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
        <br><br><input type="submit" value="+" id="addBtn"/>
        <div id="positionArea">
            <?php
                $rank = 1;
                foreach($row2 as $row){
                    echo "<div name=\"position\" id=\"position" . $rank . "\">
                    <p>Year: <input type=\"text\" name=\"year1\" value=\"".$row['year']."\">
                    <input type=\"button\" value=\"-\" onclick=\"$('#position". $rank ."').remove();return false;\"></p>
                    <textarea name=\"desc". $rank ."\"').\" rows=\"8\" cols=\"80\">".$row['description']."</textarea>
                    </div>";
                    $rank++;
                }
            ?>    
        </div>
        <p>
            <input type="hidden" name="profile_id" value="<?= $pid ?>"/>
            <input type="submit" value="Save">
            <input type="submit" name="cancel" value="Cancel">
        </p>
        
    </form>
    <script>
            var count = document.getElementsByName("position").length;
            console.log("count : "+count);

            $(document).ready(function(){
            $('#addBtn').click(function(event){
                count++;    
                event.preventDefault(); //prevent the form being submitted
                
                if(count>9){
                    alert("Cannot exceed 9 position descriptions");
                    return;
                }

                console.log("count : "+count);
                $('#positionArea').append(
                    '<div id="position' + count +'" \
                    <p>Year: <input type="text" name="year' + count + '" value="" /> \
                    <input type="button" value="-" \
                    onclick="$(\'#position'+count+'\').remove();return false;"></p> \
                    <textarea name="desc'+count+'" rows="8" cols="80"></textarea> \
                    </div>'
                );
            });
        });
        </script>               
</div>
</body>
</html>