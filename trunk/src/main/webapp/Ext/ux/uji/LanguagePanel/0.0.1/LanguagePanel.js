Ext.ns('Ext.ux.uji');

Ext.ux.uji.LanguagePanel = Ext.extend(Ext.Container,
{
    version : "0.0.1",
    name : 'tabIdiomas',
    autoHeight : true,
    frame : true,
    layoutConfig :
    {
        align : 'stretch'
    },
    language : [
    {
        lang : 'CA',
        title : 'Català'
    },
    {
        lang : 'ES',
        title : 'Espanyol'
    },
    {
        lang : 'EN',
        title : 'Anglés'
    } ],
    panelType : 'tab', // valores posibles 'tab' o 'panel',
    panelPrincipal : {},

    initComponent : function()
    {
        Ext.ux.uji.LanguagePanel.superclass.initComponent.call(this);

        this.panelPrincipal = this;

        if (this.panelType == 'tab')
        {
            this.panelPrincipal = new Ext.TabPanel(
            {
                autoHeight : true,
                frame : true,
                activeTab : 0,
                autoHeight : true,
                deferredRender : false
            });
            this.add(this.panelPrincipal);
        }

        for ( var i = 0; i < this.language.length; i++)
        {
            var newPanel = new Ext.Panel(
            {
                title : this.language[i].title,
                layout : 'form',
                frame : true,
                autoHeight : true,
                defaultType : 'textfield'
            });

            this.language[i].panel = newPanel;

            this.panelPrincipal.add(newPanel);
        }
    },

    addWidgetComponent : function(comp)
    {
        var panel = this.language[i].panel;
        var componente = comp.cloneConfig({});

        componente.name = nombreComponente + this.language[i].lang;

        panel.add(componente);
    },

    addComponent : function(comp)
    {
        var nombreComponente = comp.name;

        for ( var i = 0; i < this.language.length; i++)
        {
            var panel = this.language[i].panel;
            var componente = comp.cloneConfig({});

            componente.name = nombreComponente + this.language[i].lang;

            if (componente.hiddenName != undefined)
            {
                componente.hiddenName = nombreComponente + this.language[i].lang;
            }

            panel.add(componente);
        }
    },
    
    refreshPanels : function()
    {
        for ( var i = 0; i < this.language.length; i++)
        {
            var panel = this.language[i].panel;
            this.panelPrincipal.setActiveTab(panel);
        }
        
        this.panelPrincipal.setActiveTab(this.language[0].panel);
    },

    findComponent : function(comp, title, compRef)
    {

        if (title == null)
        {
            var idioma = compRef.name.substr(compRef.name.length - 2, compRef.name.length);

            for ( var i = 0; i < this.language.length; i++)
            {
                var panel = this.language[i].panel;

                if (this.language[i].lang == idioma)
                {
                    component = panel.find('name', comp.name + this.language[i].lang)[0];
                    return component;
                }
            }
        }
        else
        {
            {
                for ( var i = 0; i < this.language.length; i++)
                {
                    var panel = this.language[i].panel;

                    if (panel.title == title)
                    {
                        component = panel.find('name', comp.name + this.language[i].lang)[0];
                        return component;
                    }
                }
            }
        }
    },

    hideComponent : function(comp, compRef)
    {
        var component = this.findComponent(comp, null, compRef);
        component.setVisible(false);
    },

    showComponent : function(comp, compRef)
    {
        var component = this.findComponent(comp, null, compRef);
        component.setVisible(true);
    },

    setValue : function(comp, languageList)
    {
        var component;

        for ( var i = 0; i < this.language.length; i++)
        {
            component = this.findComponent(comp, this.language[i].title, null);
            component.setValue(languageList[this.language[i].lang]);
        }
        ;
    },

    setText : function(comp, languageList)
    {
        var component;

        for ( var i = 0; i < this.language.length; i++)
        {
            component = this.findComponent(comp, this.language[i].title, null);
            component.setText(languageList[this.language[i].lang]);
        }
        ;
    },

    getValue : function(comp, languageList)
    {

    }
});