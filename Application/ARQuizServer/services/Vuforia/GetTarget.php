<?php

require_once 'HTTP/Request2.php';
require_once 'SignatureBuilder.php';

// See the Vuforia Web Services Developer API Specification - https://developer.vuforia.com/resources/dev-guide/retrieving-target-cloud-database
// The GetTarget sample demonstrates how to query a single target by target id.
class GetTarget{
	
	//Server Keys
	private $access_key 	= "30c3119a2e8756ff90c467a8ae68f6067df7a659";
	private $secret_key 	= "100fbc56c990c2762fe3baafb52913b89b0b0634";

	private $url 		= "https://vws.vuforia.com";
	private $requestPath = "/targets/";// . $targetId;
	private $request;
	
	public function GetTargetInfo($targetId){

		$this->requestPath = $this->requestPath . $targetId;
		
		return $this->execGetTarget();
	}
	
	private function execGetTarget(){
		
		$this->request = new HTTP_Request2();
		$this->request->setMethod( HTTP_Request2::METHOD_GET );
		
		$this->request->setConfig(array(
				'ssl_verify_peer' => false
		));
		
		$this->request->setURL( $this->url . $this->requestPath );
		
		// Define the Date and Authentication headers
		$this->setHeaders();
		
		
		try {
		
			$response = $this->request->send();
		
			if (200 == $response->getStatus()) {
				return $response->getBody();
			} else {
                return 'Unexpected HTTP status: ' . $response->getStatus() . ' ' .
						$response->getReasonPhrase(). ' ' . $response->getBody();
			}
		} catch (HTTP_Request2_Exception $e) {
            return 'Error: ' . $e->getMessage();
		}
		
		
	}
	
	private function setHeaders(){
		$sb = 	new SignatureBuilder();
		$date = new DateTime("now", new DateTimeZone("GMT"));

		// Define the Date field using the proper GMT format
		$this->request->setHeader('Date', $date->format("D, d M Y H:i:s") . " GMT" );
		// Generate the Auth field value by concatenating the public server access key w/ the private query signature for this request
		$this->request->setHeader("Authorization" , "VWS " . $this->access_key . ":" . $sb->tmsSignature( $this->request , $this->secret_key ));

	}
}

?>