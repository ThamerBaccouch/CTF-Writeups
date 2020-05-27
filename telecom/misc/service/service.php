<?php
$points= 250;
$ticketNbr=NULL;

$banner = <<<BANNER
         _        __  __      
 _    __(_)__    / /_/ /  ___ 
| |/|/ / / _ \  / __/ _ \/ -_)
|__,__/_/_//_/  \__/_//_/\__/ 
                              

   __   ____  ________________ 
  / /  / __ \/_  __/_  __/ __ \
 / /__/ /_/ / / /   / / / /_/ /
/____/\____/ /_/   /_/  \____/ 
                               

BANNER;


echo $banner;
$username = readline("[+] Enter your name: ");
echo "\r\n[*] Welcome ".$username."\r\n";
echo "[*] A ticket cost is 1 point\r\n";
echo "[*] A winning ticket will earn you 2 points\r\n";
pMenu();
mt_srand(mt_rand()*mt_rand());
while($input=readline("[+] Enter choice > ")){
	switch ($input) {
		case "1":
			if($ticketNbr===NULL){
				$ticketNbr=readline("[+] Enter ticket number > \r\n");
				$ticketNbr=(int)$ticketNbr;
				echo "[*] You Bought ticket number: $ticketNbr\r\n";
				$points--;
			}else{
				echo "[*] You have already ticket number : $ticketNbr\r\n";
			}
			break;
		case "2":
			if($ticketNbr===NULL){
				echo "[*] Buy a ticket first\r\n";
				break;
			}
			$WinningTicket=((mt_rand()*20+1337));
			echo "[*] Drawing winning ticket .... $WinningTicket\r\n";
			if($WinningTicket===$ticketNbr){
				echo "[*] Congratz ! you have a winning ticket\r\n";
				$points+=2;
			}else{
				echo "[*] You Lost !\r\n";
			}
			$ticketNbr=NULL;
			break;
		case "3":
			if($points>=1337){
				echo "[!] Here's your flag :\r\n";
				echo file_get_contents("flag")."\r\n";
				$points-=1337;
			}else{
				echo "[*] Flag costs 1337points\r\n";
				echo "[*] You're still poor!\r\n";
			}
			break;
		case "4":
			echo "Good Bye!\r\n";
			exit;
		default:
			echo "[*] Invalid choice\r\n";
			break;
	}
	pMenu();
}
function pMenu(){
	global $points;
	echo "\r\n\r\n[*] You Have $points points\r\n".
		 "[1] Buy Ticket\r\n".
		 "[2] Play\r\n".
		 "[3] Buy Flag\r\n".
		 "[4] Exit\r\n\r\n";
}