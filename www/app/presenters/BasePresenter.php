<?php

/**
 * My Application
 *
 * @copyright  Copyright (c) 2010 John Doe
 * @package    MyApplication
 */



/**
 * Base class for all application presenters.
 *
 * @author     John Doe
 * @package    MyApplication
 */
abstract class BasePresenter extends Presenter
{
	public $oldLayoutMode = FALSE;

    public function startup() {
        parent::startup();

        LatteMacros::$defaultMacros["javadoc"] = '<?php LatteHelper::javadoc(%:macroModifiers%); ?'.'>';
    }
}
