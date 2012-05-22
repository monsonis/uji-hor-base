Ext.ns('Ext.ux.uji.form');

Ext.ux.uji.form.MultiLanguageTextArea = Ext.extend(Ext.ux.uji.form.MultiLanguageField,
{
    componentType : 'textarea',
    
    initComponent : function()
    {
        var config = {};
        Ext.apply(this, Ext.apply(this.initialConfig, config));

        Ext.ux.uji.form.MultiLanguageTextArea.superclass.initComponent.call(this);
    }
});

Ext.reg('multilanguagetextarea', Ext.ux.uji.form.MultiLanguageTextArea);