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
            
            
            $stmt = $pdo -> prepare('DELETE FROM position WHERE profile_id=:pid');
            $stmt -> execute(array(':pid'=> $_POST['profile_id'])); //delete previous information

            //subsutitue with new information
            $rank = 1;
            for($i = 1;$i<=9;$i++){
                if ( ! isset($_POST['year'.$rank])){
                    continue;
                } 
                if ( ! isset($_POST['desc'.$rank])){
                    continue;
                }

                $year = $_POST['year'.$rank]; //year
                if(!is_numeric($year)){
                    $_SESSION['error'] = 'Year must be numeric value';
                    header("Location: edit.php");
                    return;
                }
                $desc = $_POST['desc'.$rank]; //description

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

            $stmt = $pdo-> prepare('DELETE FROM education WHERE profile_id=:pid');
            $stmt -> execute(array(':pid'=>$_POST['profile_id']));

            $rank = 1;
            for($i =1; $i<=9;$i++){
                if(!isset($_POST['edu_year'.$rank])){
                    continue;
                }
                if(!isset($_POST['edu_school'.$rank])){
                    continue;
                }

                $edu_year = $_POST['edu_year'.$rank];
                if(!is_numeric($edu_year)){
                    $_SESSION['error'] = 'Year must be numeric value';
                    header("Location: edit.pdhp");
                    return;
                }
                $edu_school = $_POST['edu_school'.$rank];

                $stmt = $pdo->prepare('SELECT * FROM Institution WHERE name = :xyz');
                $stmt -> execute(array(":xyz" => $edu_school));
                $row = $stmt -> fetch(PDO::FETCH_ASSOC);
    
                if($row){
                    $institution_id = $row['institution_id'];
                }else{
                    $stmt = $pdo->prepare('INSERT INTO Institution (name) VALUES ( :name)');
                    $stmt->execute(array(
                        ':name' => $edu_school,
                    ));
                    $institution_id = $pdo->lastInsertId();
                }
    
                $stmt = $pdo -> prepare('INSERT INTO education
                (profile_id,institution_id,rank,year) VALUES (:pid, :institution, :rank, :year)');
    
                $stmt -> execute(array(
                    ':pid' => $_POST['profile_id'],
                    ':institution' => $institution_id,
                    ':rank' => $rank,
                    ':year' => $edu_year
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
    
    $stmt3 = $pdo -> prepare("SELECT * FROM education where profile_id = :pid");
    $stmt3 -> execute(array(':pid'=>$_GET['profile_id']));
    $row3 = $stmt3 -> fetchAll(PDO::FETCH_ASSOC);
    if($row3 === false){
        $_SESSION['error'] = "Bad values for profile_id";
        header("Location: index.php");
        return;
    }
?>


<html>
<head>
    <title>e8c53744</title>
    <link rel="stylesheet" 
    href="https://code.jquery.com/ui/1.12.1/themes/ui-lightness/jquery-ui.css">

    <script
    src="https://code.jquery.com/jquery-3.2.1.js"
    integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
    crossorigin="anonymous"></script>

    <script
    src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"
    integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30="
    crossorigin="anonymous"></script>
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
        <br>
        <div id="positionArea">
            <input type="submit" value="+" id="addBtn"/>
            <?php
                $rank = 1;
                foreach($row2 as $row){
                    echo "<div name=\"position\" id=\"position" . $rank . "\">
                    <p>Year: <input type=\"text\" name=\"year" . $rank . "\" value=\"".$row['year']."\">
                    <input type=\"button\" value=\"-\" onclick=\"$('#position". $rank ."').remove();return false;\"></p>
                    <textarea name=\"desc". $rank ."\"').\" rows=\"8\" cols=\"80\">".$row['description']."</textarea>
                    </div>";
                    $rank++;
                }
            ?>    
        </div>
        <br>
        <div id="educationArea">
            <input type="submit" value="+" id="addBtn2"/>
            <?php
                $rank = 1;
                foreach($row3 as $row){
                    $eid = $row['institution_id'];

                    $stmt3 = $pdo->prepare('SELECT * FROM institution WHERE institution_id = :eid');
                    $stmt3->execute(array(":eid" => $eid));
                    $row3 = $stmt3 -> fetch(PDO::FETCH_ASSOC);
                    $name = $row3['name'];
                    echo "<div name=\"education\" id=\"education" . $rank . "\">";
                    echo "<p>Year : <input type=\"text\"name=\"edu_year".$rank."\" value=\"".$row['year']."\">
                    <input type=\"button\" value=\"-\" onclick=\"$('#education".$rank."').remove();return false;\"><br>
                    <p>School : <input type=\"text\" size=\"50\" class=\"school\" name=\"edu_school".$rank."\" value=\"".$name."\"></p>
                    </div>";  
                    $rank++;                  
                }
            ?>
        </div>  
        <br>
        <p>
            <input type="hidden" name="profile_id" value="<?= $pid ?>"/>
            <input type="submit" value="Save">
            <input type="submit" name="cancel" value="Cancel">
        </p>   
    </form>
    <script>
            var count = document.getElementsByName("position").length;
            var count2 = document.getElementsByName("education").length;
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
                    onclick="$(\'#position'+count+'\').remove();count--;return false;"></p> \
                    <textarea name="desc'+count+'" rows="8" cols="80"></textarea> \
                    </div>'
                );
            });

            $('#addBtn2').click(function(event){
                count2++;
                event.preventDefault();
                if(count2>9){
                    alert("Cannot exceed 9 education inputs");
                    return;
                }

                console.log("count2 : "+count2);
                $('#educationArea').append(
                    '<div id="education' + count2 + '"> \
                    <p>Year: <input type="text" name="edu_year'+count2+'" value="" /> \
                    <input type="button" value="-" onclick="$(\'#education'+count2+ '\').remove();count2--;return false;"><br>\
                    <p>School: <input type="text" size="50" name="edu_school'+count2+'" class="school" value="" />\
                    </p></div>'
                );

                $('.school').autocomplete({
                    source: "school.php"
                });
            });
        });
        </script>               
</div>
</body>
</html>