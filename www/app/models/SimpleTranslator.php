<?php
class SimpleTranslator implements ITranslator {
  protected $lang;
  
  public function __construct ($lang) {
    $this -> lang = $lang;
  }
  
  public function translate($message,$count=NULL) {
    if ($this -> lang != 'cs') return $message;
    
    switch ($message) {
      case 'Search': return 'Hledat';
      case 'only search JCOP': return 'pouze prohledat JCOP';
      case 'Change language': return 'Změnit jazyk';
      case 'English': return 'Anglicky';
      case 'Czech': return 'Česky';
      case 'Follow us on': return 'Sledujte nás';
      case 'SourceForge bugs': return 'SourceForge chyby';
      case 'JCOP': return 'JCOP';
      case 'Introduction': return 'Úvod';
      case 'Download': return 'Stažení';
      case 'FAQ': return 'FAQ';
      case 'Basic usage': return 'Základní použití';
      case 'Installation': return 'Instalace';
      case 'Solver': return 'Solver';
      case 'Result': return 'Result (Výsledek)';
      case 'Problem': return 'Problém';
      case 'Algorithm': return 'Algoritmus';
      case 'Util': return 'Nástroje';
      case 'Advanced tweaks': return 'Pokročilé';
      case 'Appendices': return 'Přílohy';
      case 'Solvers': return 'Solvery';
      case 'Algorithms': return 'Algoritmy';
      case 'Problems': return 'Problémy';
      case 'StopConditions': return 'StopConditions (Ukončovací podmínky)';
      case 'Renders, Listeners': return 'Rendery, Listenery';
      case 'Libraries': return 'Knihovny';
      default: return $message;
    }
  }
}