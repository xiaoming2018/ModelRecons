var scene;
var camera;
var width;
var height;
// var objFileName;

function initscene() {
    scene = new THREE.Scene();
}

function initCamera() {
    debugger;
    width = $('#input').width();
    height = $('#input').height();
    camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 1000);
    camera.position.x = 0;
    camera.position.y = 0;
    camera.position.z = 2.5;
    camera.up.x = 0;
    camera.up.y = 1;
    camera.up.z = 0;
    camera.lookAt(scene.position);
}

var renderer;
function initrenderer() {
    debugger;
    width = $('#input').width();
    height = $('#input').height();
    renderer = new THREE.WebGLRenderer({
        antialias: true
    });
    renderer.setSize(width, height);
    renderer.setClearColor(0xb9d3ff, 1);//设置背景颜色(淡蓝色)
    //renderer.setClearColor(0xffffff, 1);//设置背景颜色
    renderer.shadowMap.enabled = true;    // 告诉渲染器需要
    renderer.shadowMapSoft = true; // 软阴影
    renderer.shadowMapType = THREE.PCFSoftShadowMap; //边缘柔和
    document.getElementById('input').appendChild(renderer.domElement);
}

var onProgress = function (xhr) {
    if (xhr.lengthComputable) {
        var percentComplete = xhr.loaded / xhr.total * 100;
        console.log(Math.round(percentComplete, 2) + '% downloaded');
    }
};
var onError = function (xhr) {
    console.log(xhr);
};

function loadObject() {
    debugger;
    var manager = new THREE.LoadingManager();
    THREE.Loader.Handlers.add(/\.dds$/i, new THREE.DDSLoader());
    var mtlLoader = new THREE.MTLLoader(manager);
    mtlLoader.setPath(modelFilePath);
    mtlLoader.load(modelFileIndex + '.mtl', function (materials) {
        materials.preload();
        var objLoader = new THREE.OBJLoader(manager);
        objLoader.setMaterials(materials);
        objLoader.setPath(modelFilePath);
        objLoader.load(modelFileIndex + '.obj', function (object) {
            object.traverse(function (child) {
                if (child instanceof THREE.Mesh) {
                    child.position.set(0, 0, 0);
                    child.castShadow = true;
                    child.receiveShadow = true;
                    child.scale.set(0.3,0.3,0.3);
                }
            });
            object.castShadow = true;  // 模型也产生阴影
            object.receiveShadow = true;
            scene.add(object);//将导入的模型添加到场景
        }, onProgress, onError);
    });
}

function initObject() {
    var manager = new THREE.LoadingManager();
    var texture = new THREE.Texture();
    var loader = new THREE.ImageLoader(manager);
    //loader.load('webgl_resource/models/texture/female.jpg', function (image) {

    loader.load('webgl_resource/models/cow.exr', function (image) {
        texture.image = image;
        texture.needsUpdate = true;
    });

    var material = new THREE.MeshBasicMaterial({map: texture});
    var loader = new THREE.OBJLoader(manager);
    //loader.load('webgl_resource/models/obj/Female.obj', function (object) {
    debugger;
    loader.load(pre_name + ".obj", function (object) {
        var mesh = new THREE.Mesh(object,material);
        object.traverse(function (child) {
            if (child instanceof THREE.Mesh) {
                //child.material.map = texture;
                child.position.set(0, 3, -3);
                child.castShadow = true;
                child.receiveShadow = true;
            }
        });
        object.castShadow = true;  // 模型也产生阴影
        object.receiveShadow = true;
        scene.add(object);//将导入的模型添加到场景
    });
}

//初始化灯光
var light;
var amlight;

function initLight() {
    amlight = new THREE.AmbientLight(0xFFFFFF, 0.5);
    amlight.position.set(100, 100, 100);
    scene.add(amlight);

    light = new THREE.SpotLight(0xFFFFFF, 0.5);
    // light.position.set(2, 10, 10);
    light.position.set(200, 200, 200);
    light.castShadow = true;
    light.shadowMapHeight = 2048;
    light.shadowMapWidth = 2048;
    scene.add(light);
}

var plane;

function plane_fun() {
    var planeGeometry = new THREE.PlaneGeometry(20, 20);//平面
    var planeMaterial = new THREE.MeshLambertMaterial({color: 0xffffff});
    plane = new THREE.Mesh(planeGeometry, planeMaterial);
    plane.rotation.x = -0.5 * Math.PI;//将平面沿着x轴进行旋转
    plane.position.x = 0;
    plane.position.y = -0.8;
    plane.position.z = 0;
    plane.receiveShadow = true;//平面进行接受阴影
    //scene.add(plane);
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
    controls.minDistance = 2;
    controls.maxDistance = 100;
    controls.enablePan = true;
}

function start() {
    //初始化统计对象
    initscene();
    initCamera();
    plane_fun();
    initrenderer();
    initObject();
    //loadObject();
    initLight();
    animate();
    initControls();
    document.getElementById('input').appendChild(renderer.domElement);
}

