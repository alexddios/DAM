# Valores por defecto en la configuración y nombres completos

Los valores que configura main tienen también un valor por defecto en su clase: renderFps = 60 en View, y minFps = 1 y maxFps = 120 en Presenter. main puede cambiarlos, pero si no los llama la demo sigue funcionando y no divide entre 0.

Las variables no se llaman con una letra: s pasa a sprite o state según lo que guarde. Para no chocar, el parámetro int sprite de PresenterContract (changeSpriteSpeed, moveSprite) pasa a llamarse index.

Por qué: código robusto y legible para alumnos de 2º DAM.
