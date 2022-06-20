update promoter p  set media_passagem = t.media, enterprise=t.empresa 
from teste t
where t.cpf = p.cpf;