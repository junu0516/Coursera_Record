<?php
    session_start();
    require_once "pdo.php";
           
?>
<html>
<head>
<title>e8c53744</title>
</head>
<body>
<div class="container">
    <h1>Week4 Assignment :  <?php 
        if(isset($_SESSION['loginUser'])){
            echo $_SESSION['loginUser']." logged in now";
        }else{
            echo "Nobody logged in now";
        }
    ?> 
    </h1>
    <hr>
    <?php
        if(!isset($_SESSION['loginUser'])){
            echo '<p><a href="login.php">Please log in</a></p>';
        }else{
            echo '<p><a href="logout.php">Logout</a></p>';
        }
    
    ?>
    <?php
        if(isset($_SESSION['success'])){
            echo '<p style="color:green">'.$_SESSION['success'].'</p>';
            unset($_SESSION['success']);
        }
    ?>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Headline</th>
            <th>Action</th>
        <tr>
        <?php
            $stmt = $pdo -> query("SELECT first_name, last_name, headline, profile_id from Profile");
            $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if($rows == false){
                echo
                    '<tr><th colspan="3" style="color:red">No rows found</th></tr>';
            }else{
                foreach ($rows as $row) {
                    echo "<tr><td>";
                    echo("<a href='view.php?profile_id=" . $row['profile_id'] . "'>" . $row['first_name'] .' '. $row['last_name']  . "</a>");
                    echo("</td><td>");
                    echo(htmlentities($row['headline']));
                    echo("</td><td>");
                    echo('<a href="edit.php?profile_id='.$row['profile_id'].'">Edit</a>/');
                    echo('<a href="delete.php?profile_id='.$row['profile_id'].'">Delete</a>');
                    echo("</td></tr>\n");
                }
            }
        ?>
    </table>
    <br>
    <a href="add.php">Add New Entry</a>
    <hr>
</div>
</body>
