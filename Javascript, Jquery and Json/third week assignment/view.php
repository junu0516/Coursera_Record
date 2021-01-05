<?php
require_once "pdo.php";
?>

<html>
<head>
    <title>c4e31ce9</title>
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

        echo "</ul>"

    ?>
    <a href="index.php">Done</a>
</div>
</body>
</html>