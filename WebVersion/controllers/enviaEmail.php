<?php
include '../models/EnviaEmail.php';

$Email		= $_POST["email"];	// Pega o valor do campo Email
$Mensagem	= "SenhaNova";	// Pega os valores do campo Mensagem

// Variável que junta os valores acima e monta o corpo do email

$Vai 		= "Nova senha para >> $Email\n\n Senha:: $Mensagem\n";

require_once("../phpmailer/class.phpmailer.php");

define('GUSER', 'suport@drefinancas.com');	// <-- Insira aqui o seu GMail
define('GPWD', 'thereal13');		// <-- Insira aqui a senha do seu GMail

function smtpmailer($para, $de, $de_nome, $assunto, $corpo) { 
	global $error;
	$mail = new PHPMailer();
	$mail->IsSMTP();		// Ativar SMTP
	$mail->SMTPDebug = 0;		// Debugar: 1 = erros e mensagens, 2 = mensagens apenas
	$mail->SMTPAuth = true;		// Autenticação ativada
	$mail->SMTPSecure = 'ssl';	// SSL REQUERIDO pelo GMail
	$mail->Host = 'smtp.gmail.com';	// SMTP utilizado
	$mail->Port = 587;  		// A porta 587 deverá estar aberta em seu servidor
	$mail->Username = GUSER;
	$mail->Password = GPWD;
	$mail->SetFrom($de, $de_nome);
	$mail->Subject = $assunto;
	$mail->Body = $corpo;
	$mail->AddAddress($para);
	if(!$mail->Send()) {
		$error = 'Mail error: '.$mail->ErrorInfo; 
		return false;
	} else {
		$error = 'Mensagem enviada!';
		return true;
	}
}

 if (smtpmailer($Email, 'suport@drefinancas.com', 'suporte drefinancas', 'Troca de senha', $Vai)) {
	$record = new EnviaEmail($Email , $Mensagem);
	echo $record->mudaSenha();
	echo "tudo certo";

}
if (!empty($error)){ 
	$record = new EnviaEmail($Email , $Mensagem); //QuandoEmailEnviarDeVerdadeRetirar
	echo $record->mudaSenha(); //QuandoEmailEnviarDeVerdadeRetirar
	echo $error;
}
?>
