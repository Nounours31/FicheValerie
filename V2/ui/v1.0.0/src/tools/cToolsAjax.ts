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

    /*
    public sendPostWS(url: string, iData: object = null): boolean {
        return this.localCallAjax('POST', url);
    }

    public sendDeleteWS(url: string): boolean {
        return this.localCallAjax('DELETE', url);
    }

    public sendGetWS(url: string): boolean {
        return this.localCallAjax('GET', url);
    }

    private localCallAjax(verbe : string, url : string, data? : any): boolean {
        let xhr: XMLHttpRequest = null;
        let async: boolean = false;
        if (window.XMLHttpRequest)   
            xhr = new XMLHttpRequest();
        
        if (xhr == null)
            throw "Unsuported Browser";
        
        //xhr.onreadystatechange = this.onreadystatechange.bind(xhr);
        xhr.onload = this.onreadystatechange.bind(xhr);
        xhr.open(verbe, url, async);  
        xhr.withCredentials = true;
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Cache-Control", "no-cache");
        if (verbe == 'POST')
            xhr.send(data);
        else
            xhr.send();
        return true;
    }

    private onreadystatechange(xhr: XMLHttpRequest, event: Event): any {
        if (xhr.readyState == 0) {
            console.log("request not initialized");
        }
        if (xhr.readyState == 1) {
            console.log("server connection established");
        }
        if (xhr.readyState == 2) {
            console.log("request received");
        }
        if (xhr.readyState == 3) {
            console.log("processing request");
        }
        if (xhr.readyState == 4) {
            console.log("request finished and response is ready");
            if (xhr.status === 200) {
                if ((xhr.responseText != undefined) && (xhr.responseText != null)) {
                    console.log("responseText: " + xhr.responseText);
                    this._data = JSON.parse(xhr.responseText);
                    this._status = true;
                }
                if ((xhr.responseXML != undefined) && (xhr.responseXML != null)) {
                    console.log("responseXML  ext: " + xhr.responseXML);
                    this._data = xhr.responseXML as unknown as JSON;
                    this._status = true;
                }
            }
            else {
                console.log ("httpRequest.status KO: http rc = " + xhr.status)
                this._data = {} as JSON;
                this._status = false;
            }
        }
    }
    */


    
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

    public sendDeleteWS(url: string): boolean {
        this._status = false;
        let me: cToolsAjax = this;
        let oData: object = {};

        let settings: JQueryAjaxSettings = {
            crossDomain: true,
            contentType: "application/json; charset=utf-8",
            headers: {},

            // type de retour parse par jquery
            accepts: {
                text: "application/txt",
                json: "application/json",
                xml: "application/xml",
                pdf: "application/pdf"
            },
            async: false,
            type: 'DELETE',
            timeout: 10000,

            complete: function (jqXHR: JQuery.jqXHR, textStatus: string): void {
                me.complete(jqXHR, textStatus);
            },
            success: function (data: any, textStatus: string, jqXHR: JQuery.jqXHR): void {
                me.success(data, textStatus, jqXHR);
            },
            error: this.error,
        }

        $.ajax(url, settings);
        return this._status;
    }


    public sendGetWS(url: string, forceReturnType? : string): boolean {
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
        if (forceReturnType != undefined) {
            settings.dataType = forceReturnType;
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

    
    public GetPDFFileWS(url: string, fileName: string): boolean {
        let xhr: XMLHttpRequest = new XMLHttpRequest();
        xhr.open("GET", url, true);
        xhr.responseType = 'arraybuffer';
        xhr.addEventListener('load', function () {
            if (xhr.status === 200) {
                console.log(xhr.response) // ArrayBuffer
                console.log(new Blob([xhr.response])) // Blob
                var blob = new Blob([xhr.response], { type: "application/pdf" });
 
                // -----------------------------------------------
                // Check the Browser type and download the File.
                // -----------------------------------------------
                let url: any = window.URL;
                let link = url.createObjectURL(blob);

                let a: HTMLAnchorElement = document.createElement("a");
                a.setAttribute('id', 'TemporairePourDownLoadauytzutzueyznyurtbuy');
                document.body.appendChild(a);
                a.href = link;
                a.download = fileName;
                a.click();
                a.parentElement.removeChild(a);
                window.URL.revokeObjectURL(url);
            }
        })
        xhr.send();
        return true;
    }   
}