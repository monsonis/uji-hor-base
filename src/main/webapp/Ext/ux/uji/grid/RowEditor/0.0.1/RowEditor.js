Ext.ns('Ext.ux.uji.grid');

Ext.ux.uji.grid.RowEditor = Ext.extend(Ext.ux.grid.RowEditor,
{
    version : "0.0.1",

    initComponent : function()
    {
        var config =
        {
            floating :
            {
                zindex : 7000
            }
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.ux.uji.grid.RowEditor.superclass.initComponent.call(this);
    },

    findMultiLanguageField : function(fields)
    {
        for ( var i = 0, len = fields.length; i < len; i++)
        {
            if (fields[i].xtype === 'multilanguagetextfield' || fields[i].xtype === 'multilanguagetextarea')
            {
                return fields[i];
            }
        }
    },

    stopEditing : function(saveChanges)
    {
        this.editing = false;

        if (!this.isVisible())
        {
            return;
        }

        if (saveChanges === false || !this.isValid())
        {
            this.hide();
            this.fireEvent('canceledit', this, saveChanges === false);
            return;
        }

        var changes = {};
        var r = this.record;
        var hasChange = false;
        var fields = this.items.items;
        var multiLanguageField = this.findMultiLanguageField(fields);

        for ( var i = 0, len = fields.length; i < len; i++)
        {
            var dindex = fields[i].name || fields[i].column.dataIndex;

            if (!Ext.isEmpty(dindex))
            {
                var oldValue = r.data[dindex];
                var value = r.data[dindex];

                if (fields[i].column)
                {
                    value = this.postEditValue(fields[i].getValue(), oldValue, r, dindex);
                }
                else
                {
                    var lang = dindex.substring(dindex.length - 2);
                    value = multiLanguageField.getContent(lang);
                }

                if (String(oldValue) !== String(value))
                {
                    if (fields[i].xtype === 'multilanguagetextfield' || fields[i].xtype === 'multilanguagetextarea')
                    {
                        changes[fields[i].editor.name] = value;
                    }
                    else
                    {
                        changes[dindex] = value;
                    }

                    hasChange = true;
                }
            }
        }

        if (hasChange && this.fireEvent('validateedit', this, changes, r, this.rowIndex) !== false)
        {
            r.beginEdit();
            Ext.iterate(changes, function(name, value)
            {
                r.set(name, value);
            });
            r.endEdit();
            this.fireEvent('afteredit', this, changes, r, this.rowIndex);
        }
        this.hide();
    }
});