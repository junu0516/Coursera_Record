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

    if ( isset($_POST['first_name']) && isset($_POST['last_name'])
     && isset($_POST['email']) && isset($_POST['headline']) && isset($_POST['summary'])) {

        // Data validation
        if ( strlen($_POST['first_name']) < 1 || strlen($_POST['last_name']) < 1 || strlen($_POST['email']) < 1 || strlen($_POST['headline']) < 1 || strlen($_POST['summary']) < 1) {
            $_SESSION['error'] = 'All fields are required';
            header("Location: add.php");
            return;
        }else if (strpos($_POST['email'], '@') == false) {
            $_SESSION['error'] = 'The mail must have an at (@) sign';
            header("Location: add.php");
            return;
    }else {
        $stmt = $pdo->prepare('INSERT INTO profile
        (user_id, first_name, last_name, email, headline, summary)
        VALUES ( :uid, :fn, :ln, :em, :he, :su)');
        $stmt->execute(array(
          ':uid' => $_SESSION['user_id'],
          ':fn' => $_POST['first_name'],
          ':ln' => $_POST['last_name'],
          ':em' => $_POST['email'],
          ':he' => $_POST['headline'],
          ':su' => $_POST['summary'])
        );

        $stmt = $pdo->prepare('SELECT profile_id FROM profile where first_name = :fn');
        $stmt -> execute(array(':fn' => $_POST['first_name']));
        $row = $stmt -> fetch(PDO::FETCH_ASSOC);

        if($row==false){
            $_SESSION['error'] = 'Bad value for profile_id';
            header( 'Location: add.php' ) ;
            return;
        }

        $profile_id = htmlentities($row['profile_id']);
        $rank = 1;

        for($i=1; $i <= 9; $i++){
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
              ':pid' => $profile_id,
              ':rnk' => $rank,
              ':year' => $year,
              ':descr' => $desc
            ));
            $rank++;

        }
        $_SESSION['success'] = 'Record Added';
        header( 'Location: index.php' ) ;
        return;
  }
}

?>
<html>
<head>
    <title>c4e31ce9</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
<h1>Adding Profile</h1>
    <?php
        if(isset($_SESSION['error'])){
            echo('<p style="color: red;">' . htmlentities($_SESSION['error']) . "</p>\n");
            unset($_SESSION['error']);
        }else{
            echo '<p style="color: green">Nothing happened now</p>';
        }
    ?>
<form method="post">
    <p>First Name:
    <input type="text" name="first_name" size="60"/></p>
    <p>Last Name:
    <input type="text" name="last_name" size="60"/></p>
    <p>Email:
    <input type="text" name="email" size="30"/></p>
    <p>Headline:<br/>
    <input type="text" name="headline" size="80"/></p>
    <p>Summary:<br/>
    <textarea name="summary" rows="8" cols="80"></textarea><br><br>
    <input type="submit" value="+" id="addBtn"/>
    <div id="positionInput">

    </div>
    <p>
        <input type="submit" value="Add">
        <input type="submit" name="cancel" value="Cancel">
    </p>
</form>
<script>
        var count = 0;
        $(document).ready(function(){
            $('#addBtn').click(function(event){
                count++;
                
                event.preventDefault(); //prevent the form being submitted
                
                if(count>9){
                    alert("Cannot exceed 9 position descriptions");
                    return;
                }

                console.log("count : "+count);
                $('#positionInput').append(
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