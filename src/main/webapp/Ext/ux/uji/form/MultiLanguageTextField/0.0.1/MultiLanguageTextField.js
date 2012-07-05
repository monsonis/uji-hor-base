Ext.ns('Ext.ux.uji.form');

Ext.ux.uji.form.MultiLanguageTextField = Ext.extend(Ext.ux.uji.form.MultiLanguageField,
{
    componentType : 'textfield',
    
    initComponent : function()
    {
        var config = {};
        Ext.apply(this, Ext.apply(this.initialConfig, config));

        Ext.ux.uji.form.MultiLanguageTextField.superclass.initComponent.call(this);
    }
});

Ext.reg('multilanguagetextfield', Ext.ux.uji.form.MultiLanguageTextField);