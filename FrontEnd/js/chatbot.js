function openForm() {
    document.getElementById("myForm").style.display = "block";
    document.getElementById("messaggi").innerText = "Ciao, come posso aiutarti?";
  

  }
  
  function closeForm() {
    document.getElementById("myForm").style.display = "none";
  }
  
  function risposte(testo){
     
     
    
    let text = document.getElementById("messaggi").value;
    let test = testo.toLowerCase();

   
    
    document.getElementById("messaggi").innerHTML = text+"\n \n \n"+ test;
    document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
    
    
     
   setTimeout(() => {
    if(test.includes('prenotare') || test.includes('noleggiare')){
      document.getElementById("messaggi").innerHTML = text +"\n \n \n"+ test + "\n \n \n"+"Per effettuare una prenotazione o un noleggio, loggati, "
     + "effettua un preventivo e prenota il tuo noleggio ";
    
    
   document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;


      
  }
  else if(test.includes('stampare') ||  test.includes('ricevuta') || test.includes('stampo') || test.includes('stampa')){
       
   document.getElementById("messaggi").innerHTML =text+ "\n \n \n"+ test+"\n \n \n"+  "Puoi generare un documento pdf, contenente tutti i dati del tuo noleggio, "
    + "andando nella tua area personale dove troverai tutti i tuoi noleggi, basta poi cliccare nell'apposita icona ";
 
  document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;

}
  
  else if(test.includes('preventivo') ){
      
    document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+ "Per generare un preventivo"
    + " vai nella home page e utilizza l'apposito form, ti "+
    'ricordiamo che devi loggarti per prenotare';

  document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
  }

 else if(test.includes('registrarmi') ||  test.includes('registrazione')){
      
    document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+ "Per registrarti basta"
    + "cliccare nel menu in alto sulla voce registrati e compilare l'apposito form, sarai dei nostri in un attimo! ";

 document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
    
  }

else if(test.includes('trovo le auto')){
      
    document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+ "Troverai le nostre auto"
    + " nel menu in alto sulla voce auto, che aspetti prenota subito! ";

  document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
    
  }

else if(test.includes('va') || test.includes('stai')){
      
    document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+ "Molto bene grazie,"
    + " come posso aiutarti? ";

   document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
    
  }

else if(test.includes('grazie') ){
      
    document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+ "Grazie a te per avermi contattato,"
    + " alla prossima! ";

   document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;

   setTimeout(() => {
    document.getElementById("myForm").style.display = "none";
   }, 3000);
  }

  else{
	document.getElementById("messaggi").innerHTML =text +"\n \n \n"+ test + "\n \n \n"+'Per questo tipo di richiesta ti consiglio di utilizzare '+
	'i contatti utili che troverai nella pagina assistenza e un membro del nostro team ti risponderà il prima possibile con una soluzione adatta alle tue esigenze!';
   
    document.getElementById("messaggi").scrollTop = document.getElementById("messaggi").scrollHeight;
}
  
  
  }, 3000);
}










   
 



  