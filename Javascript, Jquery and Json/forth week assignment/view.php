<?php
    require_once "pdo.php";
?>

<html>
<head>
    <title>e8c53744</title>
</head>
<body>
<div class="container">
    <h1>Profile information</h1>
    <?php
        
        echo "<ul>";

        $stmt1 = $pdo->prepare('SELECT * FROM profile where profile_id = :pid');
        $stmt1 -> execute(array(':pid' => $_GET['profile_id']));
        $row1 = $stmt1 -> fetch(PDO::FETCH_ASSOC);
        
        echo '<li>';
        echo 'First Name : '.$row1['first_name'];
        echo '</li><br>';
        echo '<li>';
        echo 'Last Name : '.$row1['last_name'];
        echo '</li><br>';
        echo '<li>';
        echo 'Email : '.$row1['email'];
        echo '</li><br>';
        echo '<li>';
        echo 'Headline : '.$row1['headline'];
        echo '</li><br>';
        echo '<li>';
        echo 'Summary : '.$row1['summary'];
        echo '</li><br>';

        $stmt2 = $pdo->prepare('SELECT * FROM position where profile_id = :pid');
        $stmt2 -> execute(array(':pid' => $_GET['profile_id']));
        
        $row2 = $stmt2 -> fetchAll(PDO::FETCH_ASSOC);
        foreach($row2 as $row){
            echo '<li>'.$row['year'].' : '.$row['description'].'</li>';
        }

        $stmt2 = $pdo->prepare('SELECT * FROM education where profile_id = :pid');
        $stmt2 -> execute(array(':pid'=>$_GET['profile_id']));

        $row2 = $stmt2 -> fetchAll(PDO::FETCH_ASSOC);
        echo '<br>';
        foreach($row2 as $row){
            $eid = $row['institution_id'];

            $stmt3 = $pdo->prepare('SELECT * FROM institution WHERE institution_id = :eid');
            $stmt3->execute(array(":eid" => $eid));
            $row3 = $stmt3 -> fetch(PDO::FETCH_ASSOC);
            $name = $row3['name'];

            echo '<li>'.$row['year'].' : '.$name.'</li>';
        }
        echo "</ul>"

    ?>
    <a href="index.php">Done</a>
</div>
</body>
</html>