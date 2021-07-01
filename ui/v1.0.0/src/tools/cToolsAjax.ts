import $ from 'jquery';
import {iPersonne} from '../WS/iWSMessages';

export default class cToolsAjax {
    private _data: JSON = null;
    private _status: boolean;
    constructor() {
        this._data = null;
        this._status = false;
    }

    public get data(): JSON {
        return this._data;
    }

    public get status(): boolean {
        return this._status;
    }

    public sendPostWS(url: string, iData: object = null): boolean {
        this._status = false;
        let me: cToolsAjax = this;
        let oData: object = {};
        if (iData !== null) {
            oData = iData;
        }

        let settings : JQueryAjaxSettings = {
            crossDomain: true,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(oData),
            headers : {},

            // type de retour parse par jquery
            dataType: 'json',
            accepts: { 
                text: "application/txt",
                json: "application/json",
                xml: "application/xml",
                pdf: "application/pdf"
            },
            async : false,
            type: 'POST',
            
            timeout: 10000,

            complete: function (jqXHR: JQuery.jqXHR, textStatus: string) : void {
                me.complete(jqXHR, textStatus);
            },
            success: function (data: any, textStatus: string, jqXHR: JQuery.jqXHR) : void {
                me.success(data, textStatus, jqXHR);
            },
            error: this.error,
        }

        $.ajax(url, settings);
        return this._status;
    }

    public sendGetWS(url: string): boolean {
        this._status = false;
        let me: cToolsAjax = this;
        let oData: object = {};

        let settings : JQueryAjaxSettings = {
            crossDomain: true,
            contentType: "application/json; charset=utf-8",
            headers : {},

            // type de retour parse par jquery
            dataType: 'json',
            accepts: { 
                text: "application/txt",
                json: "application/json",
                xml: "application/xml",
                pdf: "application/pdf"
            },
            async : false,
            type: 'GET',
            
            timeout: 10000,

            complete: function (jqXHR: JQuery.jqXHR, textStatus: string) : void {
                me.complete(jqXHR, textStatus);
            },
            success: function (data: any, textStatus: string, jqXHR: JQuery.jqXHR) : void {
                me.success(data, textStatus, jqXHR);
            },
            error: this.error,
        }

        $.ajax(url, settings);
        return this._status;
    }

    private complete (jqXHR : JQuery.jqXHR, textStatus : string) : void {
        console.log("OK Complete - status:" + this._status);
    }

    private success (data: any, textStatus : string, jqXHR : JQuery.jqXHR) : void {
        console.log("OK sucess");
        console.log("textStatus: " + textStatus);
        console.log("data: " + data);

        this._data = data; // JSON.parse(data);
        this._status = true;
    }

    private error (jqXHR : JQuery.jqXHR, textStatus : string, errorThrown: string) : void {
        console.log("OK error");
    }
}