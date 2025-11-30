import clip
import torch
from PIL import Image
import ssl

ssl._create_default_https_context = ssl._create_unverified_context
# CLIP 모델 로딩 코드
class ClipEmbeddingModel:
    def __init__(self, device="cpu"):
        self.device = device
        self.model, self.preprocess = clip.load("ViT-B/32", device=device)

    def get_embedding(self, image_file):
        image = Image.open(image_file).convert("RGB")
        image_tensor = self.preprocess(image).unsqueeze(0).to(self.device)

        with torch.no_grad():
            embedding = self.model.encode_image(image_tensor)
            embedding = embedding / embedding.norm(dim=-1, keepdim=True)

        # Tensor -> Python float list
        return embedding.squeeze().tolist()
