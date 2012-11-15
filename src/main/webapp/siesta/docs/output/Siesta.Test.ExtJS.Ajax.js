Ext.data.JsonP.Siesta_Test_ExtJS_Ajax({"tagname":"class","name":"Siesta.Test.ExtJS.Ajax","extends":null,"mixins":[],"alternateClassNames":[],"aliases":{},"singleton":false,"requires":[],"uses":[],"enum":null,"override":null,"inheritable":null,"inheritdoc":null,"meta":{},"private":null,"id":"class-Siesta.Test.ExtJS.Ajax","members":{"cfg":[],"property":[],"method":[{"name":"ajaxRequestAndThen","tagname":"method","owner":"Siesta.Test.ExtJS.Ajax","meta":{},"id":"method-ajaxRequestAndThen"},{"name":"isAjaxLoading","tagname":"method","owner":"Siesta.Test.ExtJS.Ajax","meta":{},"id":"method-isAjaxLoading"},{"name":"waitForAjaxRequest","tagname":"method","owner":"Siesta.Test.ExtJS.Ajax","meta":{},"id":"method-waitForAjaxRequest"}],"event":[],"css_var":[],"css_mixin":[]},"statics":{"cfg":[],"property":[],"method":[],"event":[],"css_var":[],"css_mixin":[]},"linenr":9,"files":[{"filename":"Ajax.js","href":"Ajax.html#Siesta-Test-ExtJS-Ajax"}],"html_meta":{},"component":false,"superclasses":[],"subclasses":[],"mixedInto":["Siesta.Test.ExtJS"],"parentMixins":[],"html":"<div><pre class=\"hierarchy\"><h4>Mixed into</h4><div class='dependency'><a href='#!/api/Siesta.Test.ExtJS' rel='Siesta.Test.ExtJS' class='docClass'>Siesta.Test.ExtJS</a></div><h4>Files</h4><div class='dependency'><a href='source/Ajax.html#Siesta-Test-ExtJS-Ajax' target='_blank'>Ajax.js</a></div></pre><div class='doc-contents'><p>This is a mixin, with helper methods for mocking Ajax functionality in Ext JS. This mixin is consumed by <a href=\"#!/api/Siesta.Test.ExtJS\" rel=\"Siesta.Test.ExtJS\" class=\"docClass\">Siesta.Test.ExtJS</a>.\nThis is only supported when testing Ext JS 4.</p>\n</div><div class='members'><div class='members-section'><div class='definedBy'>Defined By</div><h3 class='members-title icon-method'>Methods</h3><div class='subsection'><div id='method-ajaxRequestAndThen' class='member first-child not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='Siesta.Test.ExtJS.Ajax'>Siesta.Test.ExtJS.Ajax</span><br/><a href='source/Ajax.html#Siesta-Test-ExtJS-Ajax-method-ajaxRequestAndThen' target='_blank' class='view-source'>view source</a></div><a href='#!/api/Siesta.Test.ExtJS.Ajax-method-ajaxRequestAndThen' class='name expandable'>ajaxRequestAndThen</a>( <span class='pre'>String/Object url, Function callback, Object scope</span> )</div><div class='description'><div class='short'>This method calls the supplied URL using Ext.Ajax.request and then calls the provided callback. ...</div><div class='long'><p>This method calls the supplied URL using Ext.Ajax.request and then calls the provided callback. The callback will be called with the\nsame parameters as the normal Ext.Ajax.request callback is called with (\"options\", \"success\" and \"response\"). To get the response text,\nuse response.responseText.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>url</span> : String/Object<div class='sub-desc'><p>The url or the options to pass to Ext.Ajax.request</p>\n</div></li><li><span class='pre'>callback</span> : Function<div class='sub-desc'><p>The callback to call after the ajax request is completed</p>\n</div></li><li><span class='pre'>scope</span> : Object<div class='sub-desc'><p>The scope for the callback</p>\n</div></li></ul></div></div></div><div id='method-isAjaxLoading' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='Siesta.Test.ExtJS.Ajax'>Siesta.Test.ExtJS.Ajax</span><br/><a href='source/Ajax.html#Siesta-Test-ExtJS-Ajax-method-isAjaxLoading' target='_blank' class='view-source'>view source</a></div><a href='#!/api/Siesta.Test.ExtJS.Ajax-method-isAjaxLoading' class='name expandable'>isAjaxLoading</a>( <span class='pre'>[Object object], Description description</span> )</div><div class='description'><div class='short'>This assertion passes if there is at least one ongoing ajax call. ...</div><div class='long'><p>This assertion passes if there is at least one ongoing ajax call.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>object</span> : Object (optional)<div class='sub-desc'><p>The options object passed to Ext.Ajax.request</p>\n</div></li><li><span class='pre'>description</span> : Description<div class='sub-desc'><p>The description for the assertion</p>\n</div></li></ul></div></div></div><div id='method-waitForAjaxRequest' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='Siesta.Test.ExtJS.Ajax'>Siesta.Test.ExtJS.Ajax</span><br/><a href='source/Ajax.html#Siesta-Test-ExtJS-Ajax-method-waitForAjaxRequest' target='_blank' class='view-source'>view source</a></div><a href='#!/api/Siesta.Test.ExtJS.Ajax-method-waitForAjaxRequest' class='name expandable'>waitForAjaxRequest</a>( <span class='pre'>[Object object], Function callback, Object scope, Int timeout</span> )</div><div class='description'><div class='short'>Waits until the ...</div><div class='long'><p>Waits until the</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>object</span> : Object (optional)<div class='sub-desc'><p>The options object passed to Ext.Ajax.request</p>\n</div></li><li><span class='pre'>callback</span> : Function<div class='sub-desc'><p>The callback to call after the ajax request is completed</p>\n</div></li><li><span class='pre'>scope</span> : Object<div class='sub-desc'><p>The scope for the callback</p>\n</div></li><li><span class='pre'>timeout</span> : Int<div class='sub-desc'><p>The maximum amount of time to wait for the condition to be fulfilled. Defaults to the <a href=\"#!/api/Siesta.Test-cfg-waitForTimeout\" rel=\"Siesta.Test-cfg-waitForTimeout\" class=\"docClass\">Siesta.Test.waitForTimeout</a> value.</p>\n</div></li></ul></div></div></div></div></div></div></div>"});