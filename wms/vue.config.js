 
 
// const prodPlugins=[]
// if(process.env.NODE_ENV === 'production'){
//     prodPlugins.push([
//         'transform-remove-console',
//         {
//           // 保留 console.error 与 console.warn
//           exclude: ['error', 'warn']
//         }
//       ])
// }
const path = require('path') 

function resolve(dir) {
  return path.join(__dirname, dir)
}
// 上面可以删（svg）

module.exports = {
    
    publicPath: '/',
    outputDir:"dist",
    assetsDir:"static",
    productionSourceMap:false,//去除map文件，打包后体积更小，但是bug无法定位
    devServer:{
        // host:'192.168.1.161',
        port:'8081',
        open:true,
        overlay: {
            warning: true,
            error: true
        },
        proxy:{
            [process.env.VUE_APP_BASE_API]:{
                target:'http://192.168.61.101:8088',
                changeOrign:true,
                pathRewrite:{
                    ['^' + process.env.VUE_APP_BASE_API]:'/'
                }
            }
        }
    },
    css: {
        extract: false,
        loaderOptions: {
            sass: {
                prependData: `@import "~@/styles/index.scss";`
            }
        }
    },
    // xia面可以删（svg）
    chainWebpack(config) { 
    
        // set svg-sprite-loader
        config.module
          .rule('svg')
          .exclude.add(resolve('src/assets/icons'))
          .end()
        config.module
          .rule('icons')
          .test(/\.svg$/)
          .include.add(resolve('src/assets/icons'))
          .end()
          .use('svg-sprite-loader')
          .loader('svg-sprite-loader')
          .options({
            symbolId: 'icon-[name]'
          })
          .end()
    
         
      }

    //其他配置....
    // configureWebpack: {
    //     plugins: [
    //         ...prodPlugins
    //     ],
    // }
    

}