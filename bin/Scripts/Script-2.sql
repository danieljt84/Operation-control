update promoter set 
media_passagem = promoter_teste.media_passagem ,
enterprise = promoter_teste.enterprise,
cpf = promoter_teste.cpf 
from promoter_teste 
where promoter."name" = promoter_teste."name";