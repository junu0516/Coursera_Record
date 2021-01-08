<?php
    require_once "pdo.php";

    $stmt = $pdo -> prepare('SELECT name FROM Institution WHERE name Like :prefix');
    $stmt -> execute(array(':prefix' => $_REQUEST['term']."%"));
    $rset = array();
    while($row = $stmt -> fetch(PDO::FETCH_ASSOC)){
        $rset[] = $row['name'];
    }

    echo(json_encode($rset,JSON_PRETTY_PRINT));    
?>