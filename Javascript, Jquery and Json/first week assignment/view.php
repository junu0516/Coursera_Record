<?php
require_once "pdo.php";
?>

<html>
<head>
    <title>5bc8eea2</title>
</head>
<body>
<div class="container">
    <h1>Profile information</h1>
    <?php
        $stmt = $pdo->query("SELECT * FROM profile");
        echo "<ul>";
        while ( $row = $stmt->fetch(PDO::FETCH_ASSOC) ) {
            echo '<li>';
            echo 'First Name : '.$row['first_name'];
            echo '</li><br>';
            echo '<li>';
            echo 'Last Name : '.$row['last_name'];
            echo '</li><br>';
            echo '<li>';
            echo 'Email : '.$row['email'];
            echo '</li><br>';
            echo '<li>';
            echo 'Headline : '.$row['headline'];
            echo '</li><br>';
            echo '<li>';
            echo 'Summary : '.$row['summary'];
            echo '</li>';
        }
        echo "</ul>"
    ?>
    <a href="index.php">Done</a>
</div>
</body>
</html>