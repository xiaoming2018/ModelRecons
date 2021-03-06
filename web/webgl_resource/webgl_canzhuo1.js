var scene;
var camera;
var width;
var height;

function initscene() {
    scene = new THREE.Scene();
}

function initCamera() {
    debugger;
    width = $('#canvas-frame').width();
    height = $('#canvas-frame').height();
    camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 1000);
    camera.position.x = 0;
    camera.position.y = 0;
    camera.position.z = 350;
    camera.up.x = 0;
    camera.up.y = 1;
    camera.up.z = 0;
    camera.lookAt(scene.position);
}

var renderer;

function initrenderer() {
    debugger;
    width = $('#canvas-frame').width();
    height = $('#canvas-frame').height();
    renderer = new THREE.WebGLRenderer({
        antialias: true
    });
    renderer.setSize(width, height);
    renderer.setClearColor(0xb9d3ff, 1);//设置背景颜色
    renderer.shadowMap.enabled = true;    // 告诉渲染器需要
    renderer.shadowMapSoft = true; // 软阴影
    renderer.shadowMapType = THREE.PCFSoftShadowMap; //边缘柔和
    //document.getElementById('canvas-frame').appendChild(renderer.domElement);
}

var plane;

function plane_fun() {
    var planeGeometry = new THREE.PlaneGeometry(600, 600);//平面
    var planeMaterial = new THREE.MeshLambertMaterial({color: 0xffffff});
    plane = new THREE.Mesh(planeGeometry, planeMaterial);
    plane.rotation.x = -0.5 * Math.PI;//将平面沿着x轴进行旋转
    plane.position.x = 0;
    plane.position.y = -50;
    plane.position.z = 0;
    plane.receiveShadow = true;//平面进行接受阴影
    scene.add(plane);
}

function initObject() {
    var manager = new THREE.LoadingManager();
    var texture = new THREE.Texture();
    var loader = new THREE.ImageLoader(manager);
    loader.load('/ChatRobot/webgl_resource/models/texture/110.jpg', function (image) {
        texture.image = image;
        texture.needsUpdate = true;
    });

    var material = new THREE.MeshBasicMaterial({map: texture});
    var loader = new THREE.OBJLoader(manager);
    loader.load('/ChatRobot/webgl_resource/models/obj/hometrain.obj', function (object) {
        var mesh = new THREE.Mesh(object, material);
        object.traverse(function (child) {
            if (child instanceof THREE.Mesh) {
                child.material.map = texture;
                child.position.set(0, -50, 0);
                child.castShadow = true;
                child.receiveShadow = true;
            }
        });
        object.castShadow = true;  // 模型也产生阴影
        scene.add(object);//将导入的模型添加到场景
    });
}

//初始化灯光
var light;
var amlight;

function initLight() {
    amlight = new THREE.AmbientLight(0xFFFFFF, 0.5);
    amlight.position.set(300, 300, 300);
    scene.add(amlight);

    light = new THREE.SpotLight(0xFFFFFF, 1);
    light.position.set(0, 200, 200);
    light.castShadow = true;
    light.shadowMapHeight = 2048;
    light.shadowMapWidth = 2048;
    scene.add(light);
}

function animate() {
    renderer.render(scene, camera);
    requestAnimationFrame(animate);
}

//用户交互插件 鼠标左键按住旋转，右键按住平移，滚轮缩放
var controls;

function initControls() {

    controls = new THREE.OrbitControls(camera, renderer.domElement);
    controls.enableZoom = true;
    controls.autoRotate = true;
    controls.minDistance = 200;
    controls.maxDistance = 600;
    controls.enablePan = true;
}

function start_canzhuo1() {
    initscene();
    initCamera();
    initrenderer();
    plane_fun();
    initObject();
    initLight();
    animate();
    initControls();
    document.getElementById('canvas-frame').appendChild(renderer.domElement);
}

