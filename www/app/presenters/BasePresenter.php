<?php
abstract class BasePresenter extends Presenter {
	public $oldLayoutMode = FALSE;
  /** @persistent */
	public $language;

  public function startup() {
    parent::startup();

    LatteMacros::$defaultMacros["javadoc"] = '<?php LatteHelper::javadoc(%:macroModifiers%); ?'.'>';

    if ($this -> language != 'en') $this -> view = $this -> language . '.' . $this -> view;
    $this -> template -> language = $this -> language;

    $this->template->setTranslator(new SimpleTranslator($this -> language));
  }
}
