Ext.define('HOR.controller.ControllerConfiguracion',
{
    extend : 'Ext.app.Controller',

    stores : [ 'StoreHoras', 'StoreConfiguracion' ],
    model : [ 'Configuracion' ],
    refs : [
    {
        selector : 'panelConfiguracion',
        ref : 'panelConfiguracion'
    },
    {
        selector : 'configuracionCalendario',
        ref : 'configuracionCalendario'
    },
    {
        selector : 'panelConfiguracion filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'selectorHoras',
        ref : 'selectorHoras'
    } ],

    init : function()
    {
        this.control(
        {
            'panelConfiguracion filtroGrupos combobox[name=grupo]' :
            {
                select : this.cargaConfiguracion
            },
            'panelConfiguracion button[action=save]' :
            {
                click : this.guardarConfiguracion
            }
        });
    },

    cargaConfiguracion : function(combo, records)
    {
        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = combo.getValue();

        var storeConfiguracion = this.getStoreConfiguracionStore();

        storeConfiguracion.load(
        {
            params :
            {
                estudioId : estudio,
                cursoId : curso,
                semestreId : semestre,
                grupoId : grupo
            },
            scope : this,
            callback : function(records, operation, success)
            {
                var horaInicio = '01/01/2001 ';
                var horaFin = '01/01/2001 ';

                if (success)
                {
                    var record = records[0];
                    var fechaInicio = record.get('horaInicio');
                    var fechaFin = record.get('horaFin');

                    fechaInicio = fechaInicio.split(' ')[1];
                    fechaInicio = fechaInicio.substr(0, 5);
                    fechaFin = fechaFin.split(' ')[1];
                    fechaFin = fechaFin.substr(0, 5);

                    horaInicio = horaInicio + fechaInicio;
                    horaFin = horaFin + fechaFin;
                }
                else
                {
                    horaInicio = this.getStoreHorasStore().first().get('id');
                    horaFin = this.getStoreHorasStore().last().get('id');
                }

                this.getSelectorHoras().down('combobox[name=horaInicio]').setValue(horaInicio);
                this.getSelectorHoras().down('combobox[name=horaFin]').setValue(horaFin);
            }
        });
        this.getConfiguracionCalendario().show();
    },

    guardarConfiguracion : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        var estudioId = titulaciones.getValue();
        var cursoId = cursos.getValue();
        var semestreId = semestres.getValue();
        var grupoId = grupos.getValue();
        var fechaInicio = this.getSelectorHoras().down('combobox[name=horaInicio]').getValue();
        var fechaFin = this.getSelectorHoras().down('combobox[name=horaFin]').getValue();
        
        var inicio = Ext.Date.parse(fechaInicio, 'd/m/Y H:i', true);
        var fin = Ext.Date.parse(fechaFin, 'd/m/Y H:i', true);

        var storeConfiguracion = this.getStoreConfiguracionStore();
        
        var record = Ext.create("HOR.model.Configuracion", {
            estudioId: estudioId,
            cursoId: cursoId,
            semestreId: semestreId,
            grupoId: grupoId,
            fechaInicio: inicio,
            fechaFin: fin
        });
        storeConfiguracion.add(record);
        storeConfiguracion.sync();
    }
});


