// ============================================================
// common.js —— 公共函数
// 所有 HTML 页面都要引入：<script src="/common.js"></script>
// ============================================================


// ==================== 一、登录状态 ====================

// 获取当前登录的用户
// 返回：登录了 → 用户对象；没登录 → null
function getLoginUser() {
    // localStorage 是浏览器自带的存储，关掉浏览器数据还在
    // 存进去的是字符串，比如 '{"id":1,"nickname":"管理员"}'
    const str = localStorage.getItem('loginUser');

    // 没存过就是 null
    if (!str) {
        return null;
    }

    // JSON.parse 把字符串变回对象
    return JSON.parse(str);
}

// 保存登录用户（登录成功时调用）
function setLoginUser(user) {
    // JSON.stringify 把对象变成字符串，才能存进 localStorage
    localStorage.setItem('loginUser', JSON.stringify(user));
}

// ==================== token 管理（JWT）====================

// 存 token（登录成功后调用）
function setToken(token) {
    localStorage.setItem('token', token);
}

// 取 token（发请求时用）
function getToken() {
    return localStorage.getItem('token');
}

// 清掉所有登录状态（token + 用户信息）
function clearLogin() {
    localStorage.removeItem('token');
    localStorage.removeItem('loginUser');
}

// 退出登录
function logout() {
    clearLogin();              // token 和用户信息一起清
    location.href = '/';       // 跳回大厅首页
}

// 检查登录，没登录就跳到登录页
// 用在"发笔记"这种必须登录的页面
// 返回：登录了 → 用户对象；没登录 → null（并且会自动跳转）
function requireLogin() {
    const user = getLoginUser();

    if (!user) {
        alert('请先登录');
        location.href = '/login.html';      // 跳转
        return null;
    }

    return user;
}


// ==================== 二、发请求 ====================

// 统一的请求函数（所有接口都用它）
//
// 用法：
//   const notes = await api('/note/list');
//   拿到的 notes 就是后端返回的数组
async function api(url, options) {
    options = options || {};          // 没传配置就给个空对象

    // ★ 每次请求前：把 token 加到请求头（有 token 才加）
    const token = getToken();
    if (token) {
        options.headers = options.headers || {};        // 保住原有的头（比如 Content-Type）
        options.headers['Authorization'] = 'Bearer ' + token;
    }

    const res = await fetch(url, options);   // 真正发请求

    // res.ok = true 表示状态码 200~299（成功）
    if (!res.ok) {
        // ★ 401 = 未登录 或 token 无效/过期
        if (res.status === 401) {
            clearLogin();                            // 清掉本地登录状态
            alert('登录已过期，请重新登录');
            location.href = '/login.html';           // 跳登录页
            throw new Error('未登录');
        }

        const text = await res.text();       // 读错误信息
        throw new Error(text || ('请求失败 ' + res.status));
        // throw 会中断，跳到调用处的 catch
    }

    // 判断返回的是不是 JSON
    const contentType = res.headers.get('content-type') || '';

    if (contentType.includes('json')) {
        return await res.json();    // 解析成对象
    }
    return null;                    // 空响应
}

// 发 JSON 的 POST / PUT（最常用）
//
// 用法：
//   await apiJson('/note/add', 'POST', { title: '测试', userId: 1 });
//   await apiJson('/note/update', 'PUT', { id: 1, title: '改过' });
function apiJson(url, method, data) {
    return api(url, {
        method: method,                        // 'POST' 或 'PUT'
        headers: {
            'Content-Type': 'application/json' // 告诉后端"我发的是 JSON"
        },
        body: JSON.stringify(data)             // 对象 → JSON 字符串
    });
}