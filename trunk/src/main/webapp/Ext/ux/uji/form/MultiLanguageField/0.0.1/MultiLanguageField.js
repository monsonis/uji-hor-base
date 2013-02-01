Ext.ns('Ext.ux.uji.form');

var idiomas = [ 'ca', 'es', 'uk' ];

function multilang(base)
{
    var result = [];

    for ( var i = 0, j = idiomas.length; i < j; i++)
    {
        result.push(base + "__" + idiomas[i]);
    }

    return result;
}

function buildMultilangStoreColumns()
{
    var result = [];

    for ( var i = 0, a = arguments, j = a.length ; i < j ; i++)
    {
        if (typeof (arguments[i]) === "object")
        {
            result = result.concat(arguments[i]);
        }
        else
        {
            result.push(arguments[i]);
        }
    }

    return result;
}

Ext.ux.uji.form.MultiLanguageField = Ext.extend(Ext.Container,
{
    version : "0.0.1",
    layout : 'table',
    width : 300,
    language : [ 'ca', 'es', 'uk' ],
    componentType : 'textfield',
    layoutConfig :
    {
        columns : 2
    },

    initComponent : function()
    {
        var config = {};
        Ext.apply(this, Ext.apply(this.initialConfig, config));

        Ext.ux.uji.form.MultiLanguageField.superclass.initComponent.call(this);

        if (this.roweditor && !(this.roweditor instanceof Ext.ux.uji.grid.RowEditor))
        {
            alert("Es necesario utilizar Ext.ux.uji.grid.RowEditor para el componente multidioma");
        }

        this.tabList = [];
        this.languageEditors = {};

        this.initUI();
        this.generateNewHiddenFieldsFromEditor();
    },

    isRenderedInsideGrid : function()
    {
        return (this.roweditor) ? true : false;
    },

    isRenderedInsideForm : function()
    {
        return !this.isRenderedInsideGrid();
    },

    getContainer : function()
    {
        if (this.isRenderedInsideGrid())
        {
            return this.roweditor;
        }
        else
        {
            return this.findParentByType("form");
        }
    },

    generateNewHiddenFieldsFromEditor : function()
    {
        this.languageEditors[this.language[0]] = this.editor;

        for ( var i = 1; i < this.language.length; i++)
        {
            var newField = new Ext.form.Hidden(
            {
                name : this.editor.name.replace('__' + this.language[0], '__' + this.language[i]),
                width : 300
            });

            this.languageEditors[this.language[i]] = newField;
        }
    },

    getDataFromFormReader : function(field)
    {
        return Ext.DomQuery.selectValue(field, this.getContainer().reader.xmlData);
    },

    addEditorsToContainer : function()
    {
        var baseContainer = this.getContainer();

        for ( var i = 0; i < this.language.length; i++)
        {
            var editor = this.languageEditors[this.language[i]];

            if (this.isRenderedInsideForm() && !editor.getValue())
            {
                editor.setValue(this.getDataFromFormReader(editor.getName()));
            }

            baseContainer.add(editor);
        }

        if (this.isRenderedInsideForm())
        {
            baseContainer.doLayout();
        }
    },

    initUI : function()
    {
        var ref = this;

        this.initEditorIfNotDefined();

        this.button = new Ext.Button(
        {
            iconCls : 'flag-blue',
            handler : function()
            {
                ref.addEditorsToContainer();
                var height = 136;
                
                if (ref.height) {
                    height = 136 + height;
                }
                
                var window = new Ext.Window(
                {
                    title : 'Introducció de dades multidioma',
                    frame : true,
                    layout : 'fit',
                    closable : true,
                    height : height,
                    width : 400,
                    modal : true
                });

                var tabPanel = new Ext.TabPanel(
                {
                    activeTab : 0,
                    deferredRender : false,
                    padding : 8
                });

                ref.tabList = [];

                for ( var i = 0; i < ref.language.length; i++)
                {
                    var newTab = new Ext.Panel(
                    {
                        title : ref.language[i],
                        layout : 'fit',
                        items :
                        {
                            xtype : ref.componentType,
                            value : ref.languageEditors[ref.language[i]].getValue()
                        }
                    });

                    ref.tabList.push(newTab);
                    tabPanel.add(newTab);
                }

                window.add(tabPanel);

                window.addButton(
                {
                    xtype : 'button',
                    text : 'Acceptar',
                    handler : function()
                    {
                        for ( var i = 0; i < ref.language.length; i++)
                        {
                            var source = ref.tabList[i].items.items[0];
                            ref.languageEditors[ref.language[i]].setValue(source.getValue());

                            if (i === 0)
                            {
                                ref.editor.setValue(source.getValue());
                            }
                        }
                        window.close();
                    }
                });

                window.show();
            }
        });

        this.add(this.editor);
        this.add(this.button);
    },

    initEditorIfNotDefined : function()
    {
        if (!this.editor && this.name)
        {
            if (this.componentType === 'textfield')
            {
                this.editor = new Ext.form.TextField(
                {
                    name : this.name + "__" + this.language[0],
                    width : this.width - 40
                });
            }
            else if (this.componentType === 'textarea')
            {
                this.editor = new Ext.form.TextArea(
                {
                    name : this.name + "__" + this.language[0],
                    width : this.width,
                    height : this.height || 300
                });
            }
        }
        else
        {
            this.editor.name = this.editor.name + "__" + this.language[0];
        }
    },

    getContent : function(lang)
    {
        return this.languageEditors[lang].getValue();
    },

    setValue : function(value)
    {
        for ( var x = 0; x < this.language.length; x++)
        {
            this.languageEditors[this.language[x]].setValue(this.roweditor.record.data[this.editor.getName().replace('__' + this.language[0], '__' + this.language[x])]);
        }
    },

    getValue : function()
    {
        return this.languageEditors[this.language[0]].getValue();
    },

    isValid : function()
    {
        return this.editor.isValid();
    }
});