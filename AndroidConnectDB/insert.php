<?php
	$host='127.0.0.1';
	$uname='root';
	$pwd='1234';
	$db="android";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$id=$_REQUEST['id'];
	$name=$_REQUEST['name'];

	$flag['code']=0;

	if($r=mysql_query("insert into sample values('$id','$name') ",$con))
	{
		$flag['code']=1;
		
	}

	print(json_encode($flag));
	mysql_close($con);
?>