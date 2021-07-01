const path = require('path');
const { version } = require('webpack');
var webpack = require('webpack');

var pjson = require("./package.json");
module.exports = {
};


var ConfDev = {
    mode: 'development', // development' or 'production
    watch: true,
    entry: './src/main.ts',
    devtool: 'inline-source-map',
    module: {
        rules: [
            // { test: /\.tsx?$/,     use: 'babel-loader' },
            { test: /\.tsx?$/,     use: 'ts-loader', exclude: /node_modules/ },
            // is a loader chain. Which basically means that the output (return) from the right loader will be used as the input by the next loader and so on (right to left!).
            //  This loader chain will extract SASS from the SASS files, transpile it to CSS and finally to JavaScript.
            { test: /\.s[ac]ss$/i, use: [ 'style-loader',  'css-loader', 'sass-loader' ] },
            { test: /\.css$/i,     use: [ 'style-loader',  'css-loader'] },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'dist'),
    },
};

var ConfProd = {
    mode: 'production', // development' or 'production
    watch: true,
    entry: './src/main_prod.ts',
    module: {
        rules: [
            // { test: /\.tsx?$/,     use: 'babel-loader' },
            { test: /\.tsx?$/,     use: 'ts-loader', exclude: /node_modules/ },
            // is a loader chain. Which basically means that the output (return) from the right loader will be used as the input by the next loader and so on (right to left!).
            //  This loader chain will extract SASS from the SASS files, transpile it to CSS and finally to JavaScript.
            { test: /\.s[ac]ss$/i, use: [ 'style-loader',  'css-loader', 'sass-loader' ] },
            { test: /\.css$/i,     use: [ 'style-loader',  'css-loader'] },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'dist'),
    },
};



module.exports = (env) => {
    version: pjson.version

  // Use env.<YOUR VARIABLE> here:
  console.log('Version: ', version); // 'local'
  console.log('Goal: ', env.goal); // 'local'
  console.log('Production: ', env.production); // true

  if (env.production) return ConfProd;
  return ConfDev;
};
