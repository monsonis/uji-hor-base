Ext.define('HOR.controller.ControllerFiltroAulasCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreSemestresAulas', 'StoreEdificios', 'StoreTiposAula', 'StorePlantasEdificio' ],
    refs : [
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    },
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    },
    {
        selector : 'panelCalendarioPorAula',
        ref : 'panelCalendarioPorAula'
    },
    {
        selector : 'panelCalendarioDetallePorAula',
        ref : 'panelCalendarioDetallePorAula'
    },
    {
        selector : 'filtroAulas button[name=calendarioAulasDetalle]',
        ref : 'botonCalendarioDetalle'
    },
    {
        selector : 'filtroAulas button[name=calendarioAulasGenerica]',
        ref : 'botonCalendarioGenerica'
    },
    {
        selector : 'panelCalendarioAulas button[name=imprimir]',
        ref : 'botonImprimir'
    } ],

    init : function()
    {
        this.control(
        {
            'filtroAulas combobox[name=centro]' :
            {
                afterrender : this.loadCentros,
                select : this.onCentroSelected
            },
            'filtroAulas combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            },
            'filtroAulas combobox[name=edificio]' :
            {
                select : this.onEdificioSelected
            }
        });
    },

    loadCentros : function()
    {
        var comboCentros = this.getFiltroAulas().down('combobox[name=centro]');
        var store = this.getStoreCentrosStore();

        store.load(
        {
            scope : this,
            callback : function(records, operation, success)
            {
                if (success && records.length == 1)
                {
                    comboCentros.select(records[0]);
                    comboCentross.fireEvent('select');
                }
            }
        });

        fixLoadMaskBug(store, comboCentros);
    },

    onCentroSelected : function()
    {
        var comboSemestres = this.getFiltroAulas().down('combobox[name=semestre]');

        comboSemestres.clearValue();
        this.getFiltroAulas().down('combobox[name=edificio]').clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        this.getBotonImprimir().hide();

        this.getStoreEdificiosStore().removeAll();
        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();

        this.getSelectorAulas().removeAll();
        this.limpiaCalendario();

        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();

        var store = this.getStoreSemestresAulasStore();

        store.load(
        {
            params :
            {
                centroId : centro
            },
            scope : this,
            callback : function(records, operation, success)
            {
                if (success && records.length == 1)
                {
                    comboSemestres.select(records[0]);
                    comboSemestres.fireEvent('select');
                }
            }
        });

        fixLoadMaskBug(store, this.getFiltroAulas().down('combobox[name=semestre]'));
    },

    onSemestreSelected : function()
    {
        var comboEdificios = this.getFiltroAulas().down('combobox[name=edificio]');

        comboEdificios.clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        this.getBotonImprimir().hide();

        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();

        this.getSelectorAulas().removeAll();
        this.limpiaCalendario();

        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();

        var store = this.getStoreEdificiosStore();

        store.load(
        {
            params :
            {
                centroId : centro,
                semestreId : semestre
            },
            scope : this,
            callback : function(records, operation, success)
            {
                if (success && records.length == 1)
                {
                    comboEdificios.select(records[0]);
                    comboEdificios.fireEvent('select');
                }
            }
        });

        fixLoadMaskBug(store, this.getFiltroAulas().down('combobox[name=edificio]'));
    },

    onEdificioSelected : function()
    {
        var tiposAulas = this.getFiltroAulas().down('combobox[name=tipoAula]');
        var plantas = this.getFiltroAulas().down('combobox[name=planta]');

        tiposAulas.clearValue();
        plantas.clearValue();

        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var edificio = this.getFiltroAulas().down('combobox[name=edificio]').getValue();

        this.getBotonImprimir().hide();

        this.limpiaCalendario();

        var storeTipos = this.getStoreTiposAulaStore();
        var storePlantas = this.getStorePlantasEdificioStore();

        storeTipos.load(
        {
            params :
            {
                centroId : centro,
                semestreId : semestre,
                edificio : edificio
            },
            callback : function(records, operation, success)
            {
                if (success)
                {
                    if (records.length > 1)
                    {
                        this.insert(0,
                        {
                            nombre : 'Totes',
                            valor : ''
                        });
                        tiposAulas.setValue('');
                    }
                    else if (records.length > 0)
                    {
                        tiposAulas.select(records[0]);
                    }
                }
            }
        });

        fixLoadMaskBug(storeTipos, tiposAulas);

        storePlantas.load(
        {
            params :
            {
                centroId : centro,
                semestreId : semestre,
                edificio : edificio
            },
            callback : function(records, operation, success)
            {
                if (success)
                {
                    if (records.length > 1)
                    {
                        this.insert(0,
                        {
                            nombre : 'Totes',
                            valor : ''
                        });
                        plantas.setValue('');
                    }
                    else if (records.length > 0)
                    {
                        plantas.select(records[0]);
                    }
                }
            }
        });

        fixLoadMaskBug(storePlantas, plantas);
    },

    limpiaCalendario : function()
    {
        if (this.getPanelCalendarioPorAula())
        {
            this.getPanelCalendarioPorAula().limpiaCalendario();
        }

        if (this.getPanelCalendarioDetallePorAula())
        {
            this.getPanelCalendarioDetallePorAula().limpiaCalendario();
        }

        this.getBotonCalendarioDetalle().hide();
        this.getBotonCalendarioGenerica().hide();
        this.getBotonImprimir().hide();
    }

});