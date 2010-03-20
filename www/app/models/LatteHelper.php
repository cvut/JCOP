<?php

class LatteHelper {
  public static function javadoc ($class, $shortClass = null) {
    $basePath = Environment::getVariable('baseUri');
    // solver.Solver => cz.cvut.felk.cig.jcop.solver.Solver (no / at start)
    if (substr($class, 0, 1) != '/') $fullClass = 'cz.cvut.felk.cig.jcop.' . $class;
    else {
      $fullClass = $class = substr($class, 1);
    }
    preg_match ('/^(.*?)(#.*)?$/', $fullClass, $matches);
    $preHash = str_replace('.', '/', $matches[1]);
    $postHash = isset($matches[2]) ? $matches[2] : '';

    $urlClass = $preHash . '.html' . $postHash;
//    $shortClass = preg_replace('/^.*?([^.]+#.*|[^.]+)$/', '$1', $class);
    if ($shortClass === null) $shortClass = preg_replace('#^.*/#', '', $preHash) . preg_replace('/[^, ()]*\.([^., ]+)/', '$1', $postHash);

    printf ('<a class="javadoc" href="%sjavadoc/%s" title="%s">%s</a>', $basePath, $urlClass, $class, $shortClass);
  }
}