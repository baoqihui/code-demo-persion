import Vue from "vue";
import CryptoJS from "crypto-js";
import EXIF from 'exif-js'
// 数组去重
export function distinct(arr) {
  var tempArr = [].concat(arr);
  var obj = {};
  var result = [];
  for (const i of tempArr) {
    if (!obj[i]) {
      result.push(i);
      obj[i] = 1;
    }
  }
  return result;
}
// 获取两个数组中不重复的元素
export function filterArrayNoRepeat(arr1, arr2) {
  var target = [];
  var source = arr1.length > arr2.length ? arr1 : arr2;
  var assist = arr1.length > arr2.length ? arr2 : arr1;
  // console.log(source);
  // console.log(assist);
  for (const item of assist) {
    source.indexOf(item) > -1 ? source.splice(source.indexOf(item), 1) : "";
  }
  target = source;
  return target;
}

export function notification(type, title, message) {
  // 1 成功 2 错误 3 警告 4 消息
  if (type == 1) {
    Vue.prototype.$notify.success({
      title,
      message
    });
  } else if (type == 2) {
    Vue.prototype.$notify.error({
      title,
      message
    });
  } else if (type == 3) {
    Vue.prototype.$notify.warning({
      title,
      message
    });
  } else if (type == 4) {
    Vue.prototype.$notify.info({
      title,
      message
    });
  }
}

export function errorInfo(obj) {
  /**
   * obj:{
   *  code:201,
   *  message:"....",
   * }
   */
  if (obj.respCode != 200) {
    Vue.prototype.$notify.error({
      title: "提示",
      message: obj.respMessage
    });
    return false;
  }
  return true;
}

export function dateFormat(date, format) {
  if (typeof date === "string") {
    var mts = date.match(/(\/Date\((\d+)\)\/)/);
    if (mts && mts.length >= 3) {
      date = parseInt(mts[2]);
    }
  }
  date = new Date(date);
  if (!date || date.toUTCString() == "Invalid Date") {
    return "";
  }

  var map = {
    M: date.getMonth() + 1, //月份
    d: date.getDate(), //日
    h: date.getHours(), //小时
    m: date.getMinutes(), //分
    s: date.getSeconds(), //秒
    q: Math.floor((date.getMonth() + 3) / 3), //季度
    S: date.getMilliseconds() //毫秒
  };

  format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
    var v = map[t];
    if (v !== undefined) {
      if (all.length > 1) {
        v = "0" + v;
        v = v.substr(v.length - 2);
      }
      return v;
    } else if (t === "y") {
      return (date.getFullYear() + "").substr(4 - all.length);
    }
    return all;
  });
  return format;
}

// 获取页面选中内容
export function getUserSelectText() {
  return window.getSelection ?
    window.getSelection().toString() :
    document.selection.createRange().text;
}

// aes加解密
export function aesEncrypt(data) {
  var key = "dyzhcs18dlzcsjrz"; /*16位*/
  var aesKey = CryptoJS.enc.Utf8.parse(key);
  var aesPass = CryptoJS.enc.Utf8.parse(data); /*加密*/
  var encrypted = CryptoJS.AES.encrypt(aesPass, aesKey, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  return encrypted;
}
export function aesDecrypt(data) {
  var key = "dyzhcs18dlzcsjrz"; /*16位*/
  var aesKey = CryptoJS.enc.Utf8.parse(key);
  var decrypted = CryptoJS.AES.decrypt(data, aesKey, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  });
  return CryptoJS.enc.Utf8.stringify(decrypted);
}

