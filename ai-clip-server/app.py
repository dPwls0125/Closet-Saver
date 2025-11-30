
from fastapi import FastAPI, UploadFile, File
from clip_model import ClipEmbeddingModel

app = FastAPI()

clip_model = ClipEmbeddingModel(device="cpu")


@app.post("/embed/image")
async def embed_image(file: UploadFile = File(...)):
    embedding = clip_model.get_embedding(file.file)
    return {"embedding": embedding}