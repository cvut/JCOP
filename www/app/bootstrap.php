<?php

/**
 * My Application bootstrap file.
 *
 * @copyright  Copyright (c) 2010 John Doe
 * @package    MyApplication
 */



// Step 1: Load Nette Framework
// this allows load Nette Framework classes automatically so that
// you don't have to litter your code with 'require' statements
require LIBS_DIR . '/Nette/loader.php';



// Step 2: Configure environment
// 2a) enable Nette\Debug for better exception and error visualisation
Debug::enable(null, null, 'savannah@seznam.cz');

// 2b) load configuration from config.ini file
Environment::loadConfig();

// 2c) start session
$session = Environment::getSession();
$session->start();



// Step 3: Configure application
// 3a) get and setup a front controller
$application = Environment::getApplication();
$application->errorPresenter = 'Error';
//$application->catchExceptions = TRUE;



// Step 4: Setup application router
$router = $application->getRouter();

$router[] = new Route('index.php', array(
	'presenter' => 'Overview',
	'action' => 'default',
), Route::ONE_WAY);

$router[] = new Route('index.html', array(
  'presenter' => 'Overview',
  'action' => 'default',
));

$router[] = new Route('<action>.html', array(
	'presenter' => 'Overview',
	'action' => 'default',
));

$router[] = new Route('<presenter>/<action>.html', array(
	'presenter' => 'Overview',
	'action' => 'default',
));



// Step 5: Run the application!
$application->run();
