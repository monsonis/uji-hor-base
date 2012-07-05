/***************************************************************************************************
 * CKEditor Extension
 **************************************************************************************************/

Ext.form.CKEditor = function(config)
{
    this.config = config;
    Ext.form.CKEditor.superclass.constructor.call(this, config);
};

Ext.extend(Ext.form.CKEditor, Ext.form.TextArea,
{
    version : "0.0.2",
    hideLabel : true,
    constructor : function(config)
    {
        config = config || {};
        config.listeners = config.listeners || {};

        Ext.applyIf(config.listeners,
        {
            beforedestroy : this.onBeforeDestroy.createDelegate(this),
            scope : this
        });

        Ext.form.CKEditor.superclass.constructor.call(this, config);
    },

    onBeforeDestroy : function()
    {
        this.ckEditor.destroy();
    },

    onRender : function(ct, position)
    {
        if (!this.el)
        {
            this.defaultAutoCreate =
            {
                tag : "textarea",
                autocomplete : "off"
            };
        }

        Ext.form.TextArea.superclass.onRender.call(this, ct, position);
        this.ckEditor = CKEDITOR.replace(this.id, this.config.CKConfig);
    },

    setValue : function(value)
    {
        Ext.form.TextArea.superclass.setValue.apply(this, [ value ]);
        CKEDITOR.instances[this.id].setData(value);
    },

    getValue : function()
    {
        if (CKEDITOR.instances[this.id])
        {
            CKEDITOR.instances[this.id].updateElement();
            this.value = CKEDITOR.instances[this.id].getData();
            return Ext.form.TextArea.superclass.getValue.apply(this);
        }
    },

    getRawValue : function()
    {
        CKEDITOR.instances[this.id].updateElement();
        this.value = CKEDITOR.instances[this.id].getData();
        return Ext.form.TextArea.superclass.getRawValue.apply(this);
    },

    reset : function()
    {
        Ext.form.TextArea.superclass.setValue.apply(this, [ ' ' ]);
        CKEDITOR.instances[this.id].setData(' ');

        this.resetDirty();
    },

    resetDirty : function()
    {
        this.originalValue = this.getValue();
    },

    onDestroy : function()
    {
        if (CKEDITOR.instances[this.id])
        {
            delete CKEDITOR.instances[this.id]; // This destroy the instance
        }
    }
});

Ext.reg('ckeditor', Ext.form.CKEditor);